package com.hankaji.icm.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class FPTextArea extends VBox {

    Label formLabel;
    TextArea formTextArea;

    public FPTextArea(String floatingText, String promptText) {
        // Label of the claim title
        formLabel = new Label(floatingText);
        formLabel.setPadding(new Insets(0, 0, 0, 8));
        formLabel.getStyleClass().add("form-label");

        // Create TextField for the claim title
        formTextArea = new TextArea();
        formTextArea.setWrapText(true);
        formTextArea.setPromptText(promptText);
        formTextArea.getStyleClass().add("text-area");

        setSpacing(4);

        // Add the label and the TextField to the VBox
        getChildren().addAll(formLabel, formTextArea);
    }

    public Label getFormLabel() {
        return formLabel;
    }

    public TextArea getFormTextArea() {
        return formTextArea;
    }

}
