package com.hankaji.icm.app.addNewForm;

import java.util.regex.Pattern;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.TextBox;
import com.hankaji.icm.card.InsuranceCard;
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

    public AddDependent(String title) {
        super("Add new dependent");

        addField("Name", inputName);

        for (InsuranceCard c : icm.getAll()) {
            cardList.addItem(c.getCardNumber());
        }
        if (cardList.getItemCount() == 0) cardList.addItem("No card available");
        addField("Select insurance card", cardList);
    }

    @Override
    protected void onSubmit() {
        DependentManager db = DependentManager.getInstance();

        Dependent newDependent = Dependent.builder()
            .setId("c-" + ID.generateID(7))
            .setName(inputName.getText())
            .setInsuranceCard(icm.get(cardList.getSelectedItem()))
            .build();

        db.add(newDependent);
        close();
    }
    
}
