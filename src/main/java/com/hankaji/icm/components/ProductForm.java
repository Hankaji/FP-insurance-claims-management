package com.hankaji.icm.components;

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

public abstract class ProductForm extends PopupWindow {

    // Fields
    ProcessType type = ProcessType.ADD;

    // Components
    private Panel masterLayout = new Panel(new LinearLayout(Direction.VERTICAL));
    protected Panel inputFields = new Panel(createGridLayoutwithCustomMargin(2, 1, 0).setHorizontalSpacing(3));

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

    protected void addField(String fieldName, Component input) {
        inputFields.addComponent(new Label(fieldName).setLayoutData(
                GridLayout.createHorizontallyFilledLayoutData()));
        input.setLayoutData(
                GridLayout.createHorizontallyFilledLayoutData());
        inputFields.addComponent(input);
    }

    protected abstract boolean onSubmit() throws Exception;

    /**
     * Override this method to allow editing.
     * This method is called when the form is submitted
     * 
     * @return true if the form is editable
     */
    protected boolean onEdit() throws Exception {
        return false;
    }

    public ProcessType getType() {
        return type;
    }

    /**
     * <p> Set the type of the form. </p>
     * <p> This is used to determine the action to be taken when the form is submitted. </p>
     * 
     * <h4> Add mode: </h4>
     * <p> This is the default type. It is used to add new data. Class must overrive method onSubmit() in order for this to work </p>
     * 
     * <h4> Edit mode: </h4>
     * <p> This is used to edit existing data. Class must override method onEdit() and editdata() in order for this to work </p>
     * 
     * @param type the type of the form, default is ADD
     */
    public void setType(ProcessType type) {
        this.type = type;
    }

    public static enum ProcessType {
        ADD,
        EDIT
    }

}
