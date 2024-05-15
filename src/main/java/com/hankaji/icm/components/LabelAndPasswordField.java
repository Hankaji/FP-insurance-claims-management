package com.hankaji.icm.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;

public class LabelAndPasswordField extends HBox{

    public LabelAndPasswordField(Label label, PasswordField passwordField) {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(4);
        container.getStyleClass().add("text-field");
        container.getChildren().addAll(label, passwordField);

    }

}
