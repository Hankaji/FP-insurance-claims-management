package com.hankaji.icm.views.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;

public class ImageContainer extends HBox {
    private final File file;

    public ImageContainer(File file, VBox upperContent, ImageView uploadImageView, Text uploadImageText, ImageUploadForm imageUploadForm) {
        this.file = file;

        Button removeButton = new Button("X");
        removeButton.getStyleClass().add("remove-image-button");

        removeButton.setOnAction(e -> {
            upperContent.getChildren().remove(this);
            imageUploadForm.getSelectedFiles().remove(file);
            System.out.println("Removed file: " + file.getName());
            if (upperContent.getChildren().isEmpty()) {
                uploadImageView.setFitWidth(50);
                uploadImageView.setFitHeight(50);
                uploadImageView.setPreserveRatio(true);
                uploadImageText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 12));

                upperContent.getChildren().addAll(uploadImageView, uploadImageText);
            }
        });

        Label fileNameLabel = new Label(file.getName());
        getChildren().addAll(fileNameLabel, removeButton);

        setAlignment(Pos.CENTER);
        setSpacing(10);
    }

    public File getFile() {
        return file;
    }
}