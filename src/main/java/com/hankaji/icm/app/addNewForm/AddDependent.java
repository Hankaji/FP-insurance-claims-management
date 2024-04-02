package com.hankaji.icm.app.addNewForm;

import java.util.regex.Pattern;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.TextBox;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.components.AddNewForm;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;

public class AddDependent extends AddNewForm {

    // Fields
    InsuranceCardManager icm = InsuranceCardManager.getInstance();

    // Components
    final TextBox inputName = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));

    final ComboBox<String> cardList = new ComboBox<String>();

    public AddDependent() {
        this("Add new dependent");
    }

    protected AddDependent(String title) {
        super(title);

        addField("Name", inputName);

        for (InsuranceCard c : icm.getAll()) {
            cardList.addItem(c.getCardNumber() + (c.getCardHolder().isBlank() ? "" : " (" + c.getCardHolder() + ")"));
        }
        if (cardList.getItemCount() == 0) {
            cardList.addItem("No card available");
        } else {
            cardList.addItem(0, "No card selected");
            cardList.setSelectedIndex(0);
        }

        addField("Select insurance card", cardList);
    }

    @Override
    protected boolean onSubmit() throws Exception {
        DependentManager db = DependentManager.getInstance();

        if (inputName.getText().isBlank()) {
            throw new Exception("Name cannot be empty");
        }

        // Replace the selected card with the actual card Number since checklist contain
        // card number and card holder
        InsuranceCard selectedCard = icm.get(cardList.getSelectedItem().replaceAll("\\(.*?\\)", "").trim());

        Dependent newDependent = Dependent.builder()
                .setId("c-" + ID.generateID(7))
                .setName(inputName.getText())
                .setInsuranceCard(selectedCard)
                .build();
        if (selectedCard != null) {
            selectedCard.setCardHolder(newDependent.getName());
        }

        db.add(newDependent);
        return true;
    }

}
