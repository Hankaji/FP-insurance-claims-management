package com.hankaji.icm.components;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class FPComboBox<T> extends FloatingTextField {
    Label formLabel;
    ComboBox<T> comboBox;

    @SuppressWarnings("unchecked")
    public FPComboBox(String floatingText) {
        super(floatingText, new ComboBox<T>());
        comboBox = (ComboBox<T>) getSecondNode();

        // Create TextField for the claim title
        comboBox.getStyleClass().add("fp-combo-box");
    }

    public Label getFormLabel() {
        return formLabel;
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }
}
