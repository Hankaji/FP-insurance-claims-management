package com.hankaji.icm.app.addNewForm;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

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

/**
 * Represents a form for adding a new policy holder which extends the DependentForm class.
 */
public class PolicyHolderForm extends DependentForm {

    // Fields
    DependentManager depMan = DependentManager.getInstance();

    PolicyHolderManager policyHolderMan = PolicyHolderManager.getInstance();

    // Components
    DepCheckList depCheckList = new DepCheckList();

    Button openCheckList;

    /**
     * Constructs a new PolicyHolderForm with the default title "Add new policy holder".
     */
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
        InsuranceCard selectedCard = icm.getById(cardList.getSelectedItem().replaceAll("\\(.*?\\)", "").trim()).orElse(null);

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

    /**
     * A popup window that displays a list of dependents to select from.
     */
    private class DepCheckList extends PopupWindow {

        // Components
        private CheckBoxList<String> checkBoxList;

        public DepCheckList() {
            super("Select dependents");

            TerminalSize size = new TerminalSize(16, 12);
            checkBoxList = new CheckBoxList<String>(size);

            for (Dependent d : depMan.getAll()) {
                checkBoxList.addItem(d.getName());
            }

            setComponent(checkBoxList);
        }

    }

}
