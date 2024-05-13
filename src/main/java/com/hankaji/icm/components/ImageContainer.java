package com.hankaji.icm.components;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;

public class ImageContainer extends HBox {
    private final File file;
    private final MFXButton removeButton = new MFXButton("X");

    public ImageContainer(File file, VBox upperContent, ImageView uploadImageView, Text uploadImageText, ImageUploadForm imageUploadForm) {
        this.file = file;

        this.setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-radius: 8px; -fx-padding: 4px;");
        this.setMaxWidth(Region.USE_PREF_SIZE);

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

    public void setRemoveButtonVisible(boolean visible) {
        removeButton.setVisible(visible);
    }

    public MFXButton getRemoveButton() {
        return removeButton;
    }
}