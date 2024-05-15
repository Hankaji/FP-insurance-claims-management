package com.hankaji.icm.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class LabelAndTextField extends VBox {

    public LabelAndTextField(Label label, TextField textField) {
        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(4);

        label.getStyleClass().add("field-label");

        textField.getStyleClass().add("custom-text-field");
        textField.setPromptText("Enter Your" + label.getText());

        getChildren().addAll(label, textField);

    }

}
