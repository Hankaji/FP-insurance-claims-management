package com.hankaji.icm.app.addNewForm;

import static com.hankaji.icm.lib.Utils.extendsCollection;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.hankaji.icm.app.PopupWindow;

import static com.hankaji.icm.lib.Utils.LayoutUtils.createGridLayoutwithCustomMargin;

public abstract class AddNewForm extends PopupWindow {

    // Components
    private Panel masterLayout = new Panel(new LinearLayout(Direction.VERTICAL));
    private Panel inputFields = new Panel(createGridLayoutwithCustomMargin(2, 1, 0).setHorizontalSpacing(3));

    public AddNewForm(String title) {
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

        Button confirm = new Button("Confirm", this::onSubmit);
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

    protected abstract void onSubmit();

}
