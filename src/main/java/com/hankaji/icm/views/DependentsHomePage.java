package com.hankaji.icm.views;

import com.hankaji.icm.views.components.ClaimForm;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.InputStream;
import java.util.Objects;


public class DependentsHomePage extends HBox {
    private Label selectedLabel;

    public DependentsHomePage() {
        // Title and styling
        Text title = new Text("ICMS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Paint.valueOf("#c0c0c0"));

        // Wrap title in a container and add padding
        VBox titleContainer = new VBox(title);
        titleContainer.setPadding(new Insets(10, 10, 10, 10));

        // Create left part
        VBox leftPart = new VBox();
        leftPart.setStyle("-fx-background-color: #f5f5f5;");

        // Styling for left part
        leftPart.setStyle("-fx-background-color: #232C3C;");
        leftPart.setPadding(new Insets(10));

        // Set the width of the left part to be 20% of the parent width
        leftPart.prefWidthProperty().bind(this.widthProperty().multiply(0.2));
        leftPart.minWidthProperty().bind(this.widthProperty().multiply(0.2));
        leftPart.maxWidthProperty().bind(this.widthProperty().multiply(0.2));

        // Set the height of the left part to be the same as the parent height
        leftPart.prefHeightProperty().bind(this.heightProperty());
        leftPart.minHeightProperty().bind(this.heightProperty());
        leftPart.maxHeightProperty().bind(this.heightProperty());

        // Create top and bottom containers for left part
        VBox leftPartTopContainer = new VBox();
        VBox leftPartBottomContainer = new VBox();

        // Functionality labels for left part
        Label dashboardLabel = createInteractiveLabel("Dashboard", "/icons/dashboard.png");
        Label claimLabel = createInteractiveLabel("Claims", "/icons/claim.png");
        Label settingsLabel = createInteractiveLabel("Settings", "/icons/settings.png");
        Label logoutLabel = createInteractiveLabel("Log out", "/icons/logout.png");

        // Set dashboardLabel as the default option
        dashboardLabel.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c040"), new CornerRadii(8), new Insets(10, 20, 10, 20))));
        dashboardLabel.setTextFill(Color.WHITE);

        // Set dashboardLabel as the initially selected label
        selectedLabel = dashboardLabel;

        // Add labels to the containers in the left part
        leftPartTopContainer.getChildren().addAll(dashboardLabel, claimLabel);
        leftPartBottomContainer.getChildren().addAll(settingsLabel, logoutLabel);

        // Create a BorderPane and add the top and bottom containers to it
        BorderPane leftPartContainerPane = new BorderPane();
        leftPartContainerPane.setTop(leftPartTopContainer);
        leftPartContainerPane.setBottom(leftPartBottomContainer);
        leftPartContainerPane.prefHeightProperty().bind(leftPart.heightProperty());

        // Add components into left part
        leftPart.getChildren().addAll(titleContainer, leftPartContainerPane);

        // Add leftPart to the DependentsHomePage
        this.getChildren().add(leftPart);

        // Create right part
        VBox rightPart = new VBox();

        // Styling the right part
        rightPart.setPadding(new Insets(8));

        // Set the width of the right part to be 80% of the parent width
        rightPart.prefWidthProperty().bind(this.widthProperty().multiply(0.8));
        rightPart.minWidthProperty().bind(this.widthProperty().multiply(0.8));
        rightPart.maxWidthProperty().bind(this.widthProperty().multiply(0.8));

        // Set the height of the right part to be the same as the parent height
        rightPart.prefHeightProperty().bind(this.heightProperty());
        rightPart.minHeightProperty().bind(this.heightProperty());
        rightPart.maxHeightProperty().bind(this.heightProperty());

        // Create the first row for the right part
        HBox firstRow = new HBox();
        firstRow.setAlignment(Pos.CENTER_LEFT);
        firstRow.setSpacing(10);

        // Create the title for the first row
        Text firstRowTitle = new Text("My Claims");

        // Styling for the title
        firstRowTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));

        // Add the title to the first row
        firstRow.getChildren().add(firstRowTitle);

        // Create the second row for the right part
        HBox secondRow = new HBox();
        Text secondRowText = new Text("Claim Details");

        // Styling for the second row
        secondRowText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 12));

        // Add components to the second row
        secondRow.getChildren().add(secondRowText);

        // Create the third row
        HBox thirdRow = new HBox();

        // Create a ClaimForm object
        ClaimForm claimForm = new ClaimForm();

        // Add the ClaimForm to the third row
        thirdRow.setPadding(new Insets(8));
        thirdRow.setAlignment(Pos.CENTER);

        thirdRow.getChildren().add(claimForm);

        // Add rows to the right part
        rightPart.getChildren().addAll(firstRow, secondRow, thirdRow);

        // Add rightPart to the DependentsHomePage
        this.getChildren().add(rightPart);

    }

//    private StackPane getTextFieldWithImage() {
//        StackPane stackPane = new StackPane();
//
//        // Create a TextField
//        TextField searchField = getTextField();
//
//        // Load the search icon image
//        Image searchIconImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/search.png")));
//
//        // Create an ImageView for the search icon
//        ImageView searchIcon = new ImageView(searchIconImage);
//
//        // Set the size of the search icon
//        searchIcon.setFitHeight(16);
//        searchIcon.setFitWidth(16);
//
//        // Add the ImageView and TextField to the StackPane
//        stackPane.getChildren().addAll(searchField, searchIcon);
//
//        // Adjust the position of the search icon
//        StackPane.setAlignment(searchIcon, Pos.CENTER_LEFT);
//
//        return stackPane;
//    }

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
                    selectedLabel.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                    selectedLabel.setTextFill(Paint.valueOf("#c0c0c0"));
                    ((ColorAdjust) ((HBox) selectedLabel.getGraphic()).getChildren().getFirst().getEffect()).setBrightness(0.7);

                    // Update the style of the newly selected label
                    label.setBackground(new Background(new BackgroundFill(Color.web("#c0c0c040"), new CornerRadii(8), new Insets(10, 20, 10, 20))));
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
