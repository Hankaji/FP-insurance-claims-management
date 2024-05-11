package com.hankaji.icm.views.components;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

    public ImageUploadForm() {

        // Create a Pane as the background
        VBox uploadImagePane = new VBox();
        uploadImagePane.setAlignment(Pos.CENTER);
        uploadImagePane.setSpacing(20);
        uploadImagePane.setPadding(new Insets(10));
        uploadImagePane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(16), Insets.EMPTY)));

        uploadImagePane.setPrefWidth(400);

        getChildren().add(uploadImagePane);

        Image uploadImageFiles = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/file-upload.png")));
        ImageView uploadImageView = new ImageView(uploadImageFiles);
        uploadImageView.setFitWidth(100);
        uploadImageView.setPreserveRatio(true);

        Text uploadImageText = new Text("Drop Your Image Here");
        uploadImageText.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 16));

        upperContent.setAlignment(Pos.CENTER);
        upperContent.getChildren().addAll(uploadImageView, uploadImageText);

        Button uploadImageButton = getSelectButton();
        uploadImageButton.getStyleClass().add("upload-images-button");
        uploadImageButton.setPadding(new Insets(8,64,8,64));

        uploadImagePane.getChildren().addAll(upperContent, uploadImageButton);

    }

    public VBox getUploadImagePane() {
        return uploadImagePane;
    }


    public List<File> getSelectedFiles() {
        return selectedFiles;
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
                    ImageContainer imageContainer = new ImageContainer(file, upperContent, new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/file-upload.png")))), new Text("Drop Your Image Here"));
                    upperContent.getChildren().add(imageContainer);
                    System.out.println("Selected file: " + file.getName());
                }
            }
        });

        selectButton.setMinWidth(100);

        return selectButton;
    }
}