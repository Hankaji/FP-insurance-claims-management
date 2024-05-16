package com.hankaji.icm.views;

import com.hankaji.icm.views.components.ClaimForm;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.InputStream;
import java.util.Objects;

public class AddClaimPage extends HBox {
    private Label selectedLabel;

    public AddClaimPage() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm());
        this.setMaxWidth(Double.MAX_VALUE);
        this.setStyle("-fx-background-color: #fff;");
        

        // Create a ClaimForm object
        ClaimForm claimForm = new ClaimForm();


        setHgrow(claimForm, Priority.ALWAYS);

        // Add rightPart to the DependentsHomePage
        this.getChildren().add(claimForm);

    }

    // private StackPane getTextFieldWithImage() {
    // StackPane stackPane = new StackPane();
    //
    // // Create a TextField
    // TextField searchField = getTextField();
    //
    // // Load the search icon image
    // Image searchIconImage = new
    // Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png")));
    //
    // // Create an ImageView for the search icon
    // ImageView searchIcon = new ImageView(searchIconImage);
    //
    // // Set the size of the search icon
    // searchIcon.setFitHeight(16);
    // searchIcon.setFitWidth(16);
    //
    // // Add the ImageView and TextField to the StackPane
    // stackPane.getChildren().addAll(searchField, searchIcon);
    //
    // // Adjust the position of the search icon
    // StackPane.setAlignment(searchIcon, Pos.CENTER_LEFT);
    //
    // return stackPane;
    // }

    // Create a label with an icon and text
    private Label createInteractiveLabel(String text, String iconPath) {
        Label label = new Label(text);
        label.setPadding(new Insets(10, 30, 10, 30));
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setTextFill(Paint.valueOf("#c0c0c0"));
        label.setMaxWidth(Double.MAX_VALUE);

        // Create a ColorAdjust effect to make the image white
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.7);

        // Add icons before the functional labels
        InputStream inputStream = getClass().getResourceAsStream(iconPath);
        if (inputStream == null) {
            System.err.println("Could not find file: " + iconPath);
        } else {
            Image labelIcon = new Image(inputStream);
            ImageView iconView = new ImageView(labelIcon);
            iconView.setFitHeight(24); // adjust the size as needed
            iconView.setFitWidth(24); // adjust the size as needed

            // Apply the effect
            iconView.setEffect(colorAdjust);

            // Create a container for the icon and text
            HBox container = new HBox(iconView, label);
            container.setSpacing(10); // adjust the spacing as needed

            // Set the container as the label's graphic
            label.setGraphic(container);
        }

        // Add a click event handler to the label
        label.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                // If the clicked label is not the currently selected label
                if (selectedLabel != label) {
                    // Reset the style of the previously selected label
                    selectedLabel.setBackground(
                            new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                    selectedLabel.setTextFill(Paint.valueOf("#c0c0c0"));
                    ((ColorAdjust) ((HBox) selectedLabel.getGraphic()).getChildren().getFirst().getEffect())
                            .setBrightness(0.7);

                    // Update the style of the newly selected label
                    label.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c040"), new CornerRadii(8),
                            new Insets(10, 20, 10, 20))));
                    label.setTextFill(Color.WHITE);
                    colorAdjust.setBrightness(1.0);

                    // Update the reference to the newly selected label
                    selectedLabel = label;
                }
            }
        });

        return label;

    }

    private void handleLabelClick(MouseEvent event) {
        Label clickedLabel = (Label) event.getSource();
        System.out.println(clickedLabel.getText() + " clicked");
    }
}
