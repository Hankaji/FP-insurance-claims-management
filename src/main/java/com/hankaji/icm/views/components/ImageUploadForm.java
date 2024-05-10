package com.hankaji.icm.views.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUploadForm extends VBox {
    private final VBox imageInstances = new VBox();
    private static final List<File> selectedFiles = new ArrayList<>();

    public ImageUploadForm() {
        TextField fileNameField = new TextField();
        fileNameField.setPromptText("No files selected");
        fileNameField.setEditable(false);
        fileNameField.setDisable(true);

        Button selectButton = getSelectButton(fileNameField);

        Button removeButton = new Button("Remove Image");
        removeButton.setOnAction(e -> {
            if (!selectedFiles.isEmpty()) {
                selectedFiles.removeLast();
                fileNameField.setText(selectedFiles.size() + " files selected");
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(fileNameField, selectButton, removeButton);
        hbox.setSpacing(5); // Adjust the spacing as needed

        getChildren().add(hbox);
    }

    public List<File> getSelectedFiles() {
        return selectedFiles;
    }

    private static Button getSelectButton(TextField fileNameField) {
        Button selectButton = new Button("Select Images");
        selectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            List<File> newSelectedFiles = fileChooser.showOpenMultipleDialog(null);

            if (newSelectedFiles != null) {
                selectedFiles.addAll(newSelectedFiles);
                fileNameField.setText(selectedFiles.size() + " files selected");
            }
        });

        selectButton.setMinWidth(100);

        return selectButton;
    }
}