package com.hankaji.icm.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FormTextField extends VBox {

    Label formLabel;
    TextField formField;

    public FormTextField(String floatingText, String promptText) {
        // Label of the claim title
        formLabel = new Label(floatingText);
        formLabel.setPadding(new Insets(0, 0, 0, 8));
        formLabel.getStyleClass().add("form-label");

        // Create TextField for the claim title
        formField = new TextField();
        formField.setPromptText(promptText);
        formField.getStyleClass().add("form-field");

        setSpacing(4);

        // Add the label and the TextField to the VBox
        getChildren().addAll(formLabel, formField);
    }

    public Label getFormLabel() {
        return formLabel;
    }

    public TextField getFormField() {
        return formField;
    }

}
