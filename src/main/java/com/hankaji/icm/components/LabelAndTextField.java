package com.hankaji.icm.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class LabelAndTextField extends HBox {

    public LabelAndTextField(Label label, TextField textField) {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(4);
        container.getStyleClass().add("text-field");
        container.getChildren().addAll(label, textField);

    }

}
