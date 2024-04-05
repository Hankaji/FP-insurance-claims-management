package com.hankaji.icm.app.addNewForm;

import java.util.ArrayList;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.hankaji.icm.app.PopupWindow;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.PolicyHolderManager;

public class PolicyHolderForm extends DependentForm {

    // Fields
    DependentManager depMan = DependentManager.getInstance();

    PolicyHolderManager policyHolderMan = PolicyHolderManager.getInstance();

    // Components
    DepCheckList depCheckList = new DepCheckList();

    Button openCheckList;

    public PolicyHolderForm() {
        super("Add new policy holder");

        openCheckList = new Button("Open", () -> {
            getTextGUI().addWindowAndWait(depCheckList);
        });
        addField("Select dependents", openCheckList);
    }

    @Override
    protected boolean onSubmit() {
        // Replace the selected card with the actual card Number since checklist contain card number and card holder
        InsuranceCard selectedCard = icm.getById(cardList.getSelectedItem().replaceAll("\\(.*?\\)", "").trim()).get();

        PolicyHolder newPolicyHolder = PolicyHolder.builder()
                .setId("c-" + ID.generateID(7))
                .setName(inputName.getText())
                .setInsuranceCard(selectedCard)
                .setDependents(new ArrayList<String>(depCheckList.checkBoxList.getCheckedItems()))
                .build();

        if (selectedCard != null) {
            selectedCard.setCardHolder(newPolicyHolder.getName());
        }

        policyHolderMan.add(newPolicyHolder);
        return true;
    }

    private class DepCheckList extends PopupWindow {

        // Components
        private CheckBoxList<String> checkBoxList;

        public DepCheckList() {
            super("Select dependents");

            TerminalSize size = new TerminalSize(16, 10);
            checkBoxList = new CheckBoxList<String>(size);

            for (Dependent d : depMan.getAll()) {
                checkBoxList.addItem(d.getName());
            }
            for (PolicyHolder ph : policyHolderMan.getAll()) {
                checkBoxList.addItem(ph.getName());
            }

            setComponent(checkBoxList);
        }

    }

}
