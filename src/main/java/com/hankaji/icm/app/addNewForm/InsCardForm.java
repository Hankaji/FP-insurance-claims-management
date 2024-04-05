package com.hankaji.icm.app.addNewForm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.components.ProductForm;
import com.hankaji.icm.customer.Customer;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;
import com.hankaji.icm.services.PolicyHolderManager;

public class InsCardForm extends ProductForm {

    // Fields
    InsuranceCardManager icm = InsuranceCardManager.getInstance();

    DependentManager depMan = DependentManager.getInstance();

    PolicyHolderManager policyHolderMan = PolicyHolderManager.getInstance();

    ArrayList<Customer> customers;

    String hasCardFormat = " (has card)";

    String emptyListStr = "No customers available";

    // Components
    final ComboBox<String> ownerList = new ComboBox<>();

    final TextBox inputOwner = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));

    final ComboBox<String> inputExpirationDate = new ComboBox<>("1 year", "2 years", "3 years", "4 years", "5 years");

    public InsCardForm() {
        super("Add new card");

        // Get all customers
        customers = new ArrayList<>(DependentManager.getInstance().getAll());
        customers.addAll(PolicyHolderManager.getInstance().getAll());

        for (Customer c : customers) {
            // Add customer to the owner list, with a note if they have a card
            ownerList.addItem(
                    c.getName() + " (" + c.getId() + ")" + (c.getInsuranceCard() == null ? "" : hasCardFormat));
        }

        // If no customers are available, add a message to the owner list
        if (ownerList.getItemCount() == 0)
            ownerList.addItem(emptyListStr);

        // Add components
        addField("Card holder", ownerList);
        addField("Policy owner", inputOwner);
        addField("Expiration date", inputExpirationDate);
    }

    @Override
    protected boolean onSubmit() throws Exception {
        LocalDateTime expirationDate = LocalDateTime.now()
                .plusYears(Long.parseLong(
                        inputExpirationDate
                                .getSelectedItem()
                                .replaceAll("\\byear(s?)\\b", "")
                                .trim()));

        // Check if the card holder already has a card
        // If they do, ask if they want to replace it
        String holder = ownerList.getSelectedItem();
        holder = holder == emptyListStr ? "" : holder; // If no customers are available, set holder to empty string

        final String nameOnly = holder.replaceAll("\\(.*?\\)", "").trim();
        if (holder.contains(hasCardFormat)) {
            MessageDialogButton answer = MessageDialog.showMessageDialog(
                    getTextGUI(),
                    "Confirmation",
                    "This user already holding a card, would you wish to change?",
                    MessageDialogButton.Cancel, MessageDialogButton.OK);

            switch (answer) {
                case MessageDialogButton.OK:
                    break;
                default:
                    return false;
            }

            // Remove the the holder from the card
            try {
                Customer holderObj = customers.stream()
                        .filter(c -> c.getName().equals(nameOnly))
                        .findFirst()
                        .get();

                String otherCardNum = holderObj.getInsuranceCard().getCardNumber();
                InsuranceCard otherCard = icm.getById(otherCardNum).get();
                otherCard.setCardHolder("");
            } catch (Exception e) {
                throw new Exception("Error removing card from holder");
            }
        }

        // Create the new card
        InsuranceCard newCard = InsuranceCard.builder()
                .setCardNumber(ID.generateID(10))
                .setCardHolder(holder)
                .setPolicyOwner(inputOwner.getText())
                .setExpirationDate(expirationDate)
                .build();

        // Add the card to the card holder
        if (holder != emptyListStr) {
            Customer holderObj = customers.stream()
                    .filter(c -> c.getName().equals(nameOnly))
                    .findFirst()
                    .get();

            holderObj.setInsuranceCard(newCard);
        }

        // Add the card to the database
        icm.add(newCard);
        return true;
    }

}
