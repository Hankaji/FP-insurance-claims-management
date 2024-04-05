package com.hankaji.icm.components;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: Lanterna, Gson, Apache Commons IO
*/

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.hankaji.icm.app.PopupWindow;

import static com.hankaji.icm.lib.Utils.LayoutUtils.createGridLayoutwithCustomMargin;

/**
 * A form for adding or editing products.
 */
public abstract class ProductForm extends PopupWindow {

    // Fields
    ProcessType type = ProcessType.ADD;

    // Components
    private Panel masterLayout = new Panel(new LinearLayout(Direction.VERTICAL));
    protected Panel inputFields = new Panel(createGridLayoutwithCustomMargin(2, 1, 0).setHorizontalSpacing(3));

    /**
     * Constructs a ProductForm object with the specified title.
     * 
     * @param title the title of the form
     */
    public ProductForm(String title) {
        super(title);

        // divider
        EmptySpace devider = new EmptySpace();

        // Form actions
        Panel buttonContainer = new Panel(createGridLayoutwithCustomMargin(2, 0));
        buttonContainer.setLayoutData(
                LinearLayout.createLayoutData(LinearLayout.Alignment.Fill, LinearLayout.GrowPolicy.CanGrow));

        Button cancel = new Button("Cancel", () -> close());
        cancel.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.END,
                GridLayout.Alignment.END,
                true,
                false));

        Button confirm = new Button("Confirm", () -> {
            // If onSubmit returns true, close the window
            // This is used in case error were made to allow correction
            try {
                switch (type) {
                    case ProcessType.ADD:
                        if (onSubmit()) close();
                        break;
                    case ProcessType.EDIT:
                        if (onEdit()) close();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                // e.printStackTrace();
                MessageDialog.showMessageDialog(
                        getTextGUI(),
                        "Error",
                        e.getMessage(),
                        MessageDialogButton.Close);
            }
        });
        confirm.setLayoutData(GridLayout.createLayoutData(
                GridLayout.Alignment.END,
                GridLayout.Alignment.END,
                false,
                false));

        // --------------------------------------------------
        // Add components
        // --------------------------------------------------
        masterLayout.addComponent(inputFields);
        masterLayout.addComponent(devider);

        buttonContainer.addComponent(cancel);
        buttonContainer.addComponent(confirm);

        masterLayout.addComponent(buttonContainer);

        setComponent(masterLayout);
    }

    /**
     * Adds a field to the form with the specified field name and input component.
     * 
     * @param fieldName the name of the field
     * @param input the input component for the field
     */
    protected void addField(String fieldName, Component input) {
        inputFields.addComponent(new Label(fieldName).setLayoutData(
                GridLayout.createHorizontallyFilledLayoutData()));
        input.setLayoutData(
                GridLayout.createHorizontallyFilledLayoutData());
        inputFields.addComponent(input);
    }

    /**
     * This method is called when the form is submitted.
     * Subclasses must override this method to handle form submission.
     * 
     * @return true if the form submission is successful, false otherwise
     * @throws Exception if an error occurs during form submission
     */
    protected abstract boolean onSubmit() throws Exception;

    /**
     * Override this method to allow editing.
     * This method is called when the form is submitted.
     * 
     * @return true if the form is editable, false otherwise
     * @throws Exception if an error occurs during form submission
     */
    protected boolean onEdit() throws Exception {
        return false;
    }

    /**
     * Returns the type of the form.
     * 
     * @return the type of the form
     */
    public ProcessType getType() {
        return type;
    }

    /**
     * Sets the type of the form.
     * This is used to determine the action to be taken when the form is submitted.
     * 
     * @param type the type of the form, default is ADD
     */
    public void setType(ProcessType type) {
        this.type = type;
    }

    /**
     * Process type of the form.
     * When using EDIT, onEdit() method will be called instead of onSubmit().
     */
    public static enum ProcessType {
        ADD,
        EDIT
    }

}
