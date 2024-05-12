package com.hankaji.icm.views.components;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageUploadForm extends VBox {
    private final VBox uploadImagePane = new VBox();
    private static final List<File> selectedFiles = new ArrayList<>();
    private final VBox upperContent = new VBox();
    private final ImageView uploadImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/file-upload.png"))));
    private final Text uploadImageText = new Text("Drop Your Image Here");

    private final double initialUploadImageViewFitWidth;
    private final Font initialUploadImageTextFont;

    public ImageUploadForm() {

        // Create a Pane as the background
        uploadImagePane.setAlignment(Pos.CENTER);
        uploadImagePane.setSpacing(12);
        uploadImagePane.getStyleClass().add("upload-images-pane");
        getChildren().add(uploadImagePane);

        Image uploadImageFiles = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/file-upload.png")));
        ImageView uploadImageView = new ImageView(uploadImageFiles);
        uploadImageView.setFitWidth(50);
        uploadImageView.setFitHeight(50);
        uploadImageView.setPreserveRatio(true);

        initialUploadImageViewFitWidth = uploadImageView.getFitWidth();
        initialUploadImageTextFont = uploadImageText.getFont();


        Text uploadImageText = new Text("Drop Your Image Here");
        uploadImageText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 14));

        upperContent.setAlignment(Pos.CENTER);
        upperContent.setSpacing(8);
        upperContent.getChildren().addAll(uploadImageView, uploadImageText);

        Button uploadImageButton = getSelectButton();
        uploadImageButton.getStyleClass().add("upload-images-button");

        uploadImagePane.getChildren().addAll(upperContent, uploadImageButton);

    }

    public VBox getUploadImagePane() {
        return uploadImagePane;
    }


    public List<File> getSelectedFiles() {
        return selectedFiles;
    }

    public void clearSelectedFiles() {
        selectedFiles.clear();
        upperContent.getChildren().clear();
        uploadImageView.setFitWidth(initialUploadImageViewFitWidth);
        uploadImageView.setPreserveRatio(true);
        uploadImageText.setFont(initialUploadImageTextFont);
        upperContent.getChildren().addAll(uploadImageView, uploadImageText);
    }

    private Button getSelectButton() {
        Button selectButton = new Button("Upload");
        selectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            List<File> newSelectedFiles = fileChooser.showOpenMultipleDialog(null);

            if (newSelectedFiles != null) {
                selectedFiles.addAll(newSelectedFiles);
                upperContent.getChildren().clear();
                for (File file : newSelectedFiles) {
                    ImageContainer imageContainer = new ImageContainer(file, upperContent, uploadImageView, uploadImageText, this);
                    upperContent.getChildren().add(imageContainer);
                    System.out.println("Selected file: " + file.getName());
                }
            }

        });

        selectButton.setMinWidth(100);

        return selectButton;
    }
}