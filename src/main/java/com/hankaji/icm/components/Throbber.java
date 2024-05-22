package com.hankaji.icm.components;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Throbber extends StackPane {

    private MFXProgressSpinner spinner;

    public Throbber() {
        getStyleClass().add("throbber");
        spinner = new MFXProgressSpinner();

        HBox.setHgrow(spinner, Priority.ALWAYS);
        VBox.setVgrow(spinner, Priority.ALWAYS);

        setAlignment(Pos.CENTER);

        getChildren().add(spinner);
    }

    public MFXProgressSpinner getSpinner() {
        return spinner;
    }
}
