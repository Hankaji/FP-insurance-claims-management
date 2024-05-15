package com.hankaji.icm.components;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FPPasswordField extends FloatingTextField {
    Label formLabel;
    PasswordField formField;

    public FPPasswordField(String floatingText, String promptText) {
        super(floatingText, new PasswordField());
        formField = (PasswordField) getSecondNode();

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
