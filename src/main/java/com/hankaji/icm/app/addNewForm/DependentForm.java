package com.hankaji.icm.app.addNewForm;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/
import java.util.regex.Pattern;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.TextBox;
import com.hankaji.icm.card.InsuranceCard;
import com.hankaji.icm.components.ProductForm;
import com.hankaji.icm.customer.Dependent;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.services.DependentManager;
import com.hankaji.icm.services.InsuranceCardManager;

/** 
 * Form for adding a new dependent which extends the ProductForm class.
 */
public class DependentForm extends ProductForm {

    // Fields
    InsuranceCardManager icm = InsuranceCardManager.getInstance();

    // Components
    final TextBox inputName = new TextBox().setValidationPattern(Pattern.compile("[A-Za-z ]*"));

    final ComboBox<String> cardList = new ComboBox<String>();

    /**
     * Constructs a new DependentForm with the default title "Add new dependent".
     */
    public DependentForm() {
        this("Add new dependent");
    }

    /**
     * Constructs a new DependentForm with the specified title.
     * 
     * @param title the title of the form
     */
    protected DependentForm(String title) {
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

    /**
     * Overrides the onSubmit method from the ProductForm class.
     * This method is called when the form is submitted.
     * It adds a new dependent to the database and updates the selected insurance card.
     * 
     * @return true if the form submission is successful, false otherwise
     * @throws Exception if the name is empty
     */
    @Override
    protected boolean onSubmit() throws Exception {
        DependentManager db = DependentManager.getInstance();

        if (inputName.getText().isBlank()) {
            throw new Exception("Name cannot be empty");
        }

        // Replace the selected card with the actual card Number since checklist contain
        // card number and card holder
        InsuranceCard selectedCard = icm.getById(cardList.getSelectedItem().replaceAll("\\(.*?\\)", "").trim()).orElse(null);

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
