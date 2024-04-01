package com.hankaji.icm.app.addNewForm;

import java.util.ArrayList;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.hankaji.icm.app.PopupWindow;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.customer.PolicyHolder;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.PolicyHolderManager;

public class AddPolicyHolder extends AddDependent {

    // Fields
    DependentManager depMan = DependentManager.getInstance();

    PolicyHolderManager policyHolderMan = PolicyHolderManager.getInstance();

    // Components
    DepCheckList depCheckList = new DepCheckList();

    Button openCheckList;

    public AddPolicyHolder() {
        super("Add new policy holder");

        openCheckList = new Button("Open", () -> {
            getTextGUI().addWindowAndWait(depCheckList);
        });
        addField("Select dependents", openCheckList);
    }

    @Override
    protected void onSubmit() {

        PolicyHolder newPolicyHolder = PolicyHolder.builder()
            .setId("c-" + ID.generateID(7))
            .setName(inputName.getText())
            .setInsuranceCard(icm.get(cardList.getSelectedItem()))
            .setDependents(new ArrayList<String>(depCheckList.checkBoxList.getCheckedItems()))
            .build();
        
        policyHolderMan.add(newPolicyHolder);
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
