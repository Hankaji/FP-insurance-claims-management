package com.hankaji.icm.app.addNewForm;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.TextBox;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.components.AddNewForm;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;
import com.hankaji.icm.services.PolicyHolderManager;

public class AddNewCard extends AddNewForm {

    // Fields
    InsuranceCardManager icm = InsuranceCardManager.getInstance();

    DependentManager depMan = DependentManager.getInstance();

    PolicyHolderManager policyHolderMan = PolicyHolderManager.getInstance();

    // Components
    final ComboBox<String> ownerList = new ComboBox<>();

    final TextBox inputOwner = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));

    final ComboBox<String> inputExpirationDate = new ComboBox<>("1 year", "2 years", "3 years", "4 years", "5 years");

    public AddNewCard() {
        super("Add new card");

        for (Dependent d : depMan.getAll()) {
            ownerList.addItem(d.getName() + " (" + d.getId() + ")");
        }
        for (PolicyHolder ph : policyHolderMan.getAll()) {
            ownerList.addItem(ph.getName() + " (" + ph.getId() + ")");
        }

        // Add components
        addField("Card holder", ownerList);
        addField("Policy owner", inputOwner);
        addField("Expiration date", inputExpirationDate);
    }

    @Override
    protected void onSubmit() {
        // InsuranceCardManager db = InsuranceCardManager.getInstance();

        LocalDateTime expirationDate = LocalDateTime.now()
                .plusYears(Long.parseLong(
                        inputExpirationDate
                                .getSelectedItem()
                                .replace("years", "")
                                .trim()));

        InsuranceCard newCard = InsuranceCard.builder()
                .setCardNumber(ID.generateID(10))
                .setCardHolder(ownerList.getSelectedItem())
                .setPolicyOwner(inputOwner.getText())
                .setExpirationDate(expirationDate)
                .build();

        icm.add(newCard);
    }

}
