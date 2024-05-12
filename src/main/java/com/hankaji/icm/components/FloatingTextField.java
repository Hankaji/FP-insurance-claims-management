package com.hankaji.icm.components;

import java.util.Objects;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class FloatingTextField extends VBox {
    Label formLabel;
    Node secondNode;

    public FloatingTextField(String floatingText, Node secondNode) {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm());

        // Label of the claim title
        formLabel = new Label(floatingText);
        formLabel.setPadding(new Insets(0, 0, 0, 8));
        formLabel.getStyleClass().add("form-label");

        // Set second Node
        this.secondNode = secondNode;

        setSpacing(4);

        // Add the label and the TextField to the VBox
        getChildren().addAll(formLabel, secondNode);
    }

    public Label getFormLabel() {
        return formLabel;
    }

    public Node getSecondNode() {
        return secondNode;
    }
}
