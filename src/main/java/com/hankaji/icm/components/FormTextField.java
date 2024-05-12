package com.hankaji.icm.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FormTextField extends FloatingTextField {

    Label formLabel;
    TextField formField;

    public FormTextField(String floatingText, String promptText) {
        super(floatingText, new TextField());
        formField = (TextField) getSecondNode();

        // Create TextField for the claim title
        formField.setPromptText(promptText);
        formField.getStyleClass().add("form-field");
    }

    public Label getFormLabel() {
        return formLabel;
    }

    public TextField getFormField() {
        return formField;
    }

}
