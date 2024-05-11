package com.hankaji.icm.views.components;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javafx.scene.layout.GridPane.setHalignment;
import static javafx.scene.layout.GridPane.setValignment;

public class ClaimForm extends VBox {

    public enum Status {
        NEW,
        PROCESSING,
        DONE
    }

    public ClaimForm() {
        // Create a GridPane
        GridPane gridPane = new GridPane();

        // Set the gridPane properties
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(16));

        // Create an HBox for the title and the Save button
        HBox titleAndSaveButton = new HBox();
        titleAndSaveButton.setSpacing(10); // Adjust the spacing as needed
        titleAndSaveButton.setMaxWidth(Double.MAX_VALUE);
        titleAndSaveButton.setPadding(new Insets(16));

        // Create the Form Title and Styling
        Label formTitle = new Label("Claim Form");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 32));
        titleAndSaveButton.getChildren().add(formTitle);

        // Create a spacer node
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        titleAndSaveButton.getChildren().add(spacer);

        // Create a Clear Button
        MFXButton clearButton = new MFXButton("Clear");
        HBox.setHgrow(clearButton, Priority.ALWAYS);
        titleAndSaveButton.getChildren().add(clearButton);

        // Create a Submit Button
        MFXButton submitButton = new MFXButton("Submit");
        HBox.setHgrow(submitButton, Priority.ALWAYS);
        titleAndSaveButton.getChildren().add(submitButton);

        getChildren().add(titleAndSaveButton);

        // Add the GridPane to the VBox
        getChildren().add(gridPane);

        // Set the column constraints for the GridPane
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(60);
        gridPane.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40);
        gridPane.getColumnConstraints().add(column2);

        // Create a VBox for the left column
        VBox leftColumn = new VBox();
        leftColumn.setSpacing(10);
        leftColumn.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(16), Insets.EMPTY)));
        gridPane.add(leftColumn, 0, 0);
        leftColumn.prefHeightProperty().bind(leftColumn.heightProperty());
        leftColumn.setPadding(new Insets(16, 24, 16, 24));

        Text leftTitle = new Text("Details");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 18));
        setHalignment(leftTitle, HPos.LEFT);

        // Styling for the Clear Button
        clearButton.getStyleClass().add("clear-button");

        // Styling for the Submit Button
        submitButton.getStyleClass().add("submit-button");

        // Label of the claim title
        Label claimTitleLabel = new Label("Claim Title");

        // Create TextField for the claim title
        TextField claimTitle = new TextField();
        claimTitle.setPromptText("Ex: Car Accident Claim");

        // Validate the claim title to only letters and spaces and ignore non-alphabetical input
        claimTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                claimTitle.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        // Label of the claim description
        Label claimDescriptionLabel = new Label("Claim Description");

        // Create TextField for the claim description
        TextField claimDescription = new TextField();
        claimDescription.setPromptText("Ex: I was involved in a car accident on 12/12/2021");

        // Set a maximum character limit
        final int maxCharacterCount = 400; // Set the maximum character count
        claimDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxCharacterCount) {
                claimDescription.setText(oldValue);
            }
        });

        // Create a label to display the used characters out of the total allowable characters
        Label characterCountLabel = new Label();
        characterCountLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        "Current characters: " + claimDescription.getText().length() + "/" + maxCharacterCount,
                claimDescription.textProperty()));

        // Label of the claim amount
        Label claimAmountLabel = new Label("Claim Amount ($)");

        // Create TextField for the claim amount
        TextField claimAmount = new TextField();
        claimAmount.setPromptText("Ex: 1000");

        // Validate the claim amount and ignore non-numeric input
        claimAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                claimAmount.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Label of the received banking info
        Label receivedBankingInfoLabel = new Label("Received Banking Info");

        // Create TextField for the received banking info
        TextField receivedBankingInfo = new TextField();
        receivedBankingInfo.setPromptText("Ex: ACB-John Doe-1234567890");

        // Add the components to the left column and styling
        leftColumn.getChildren().addAll(leftTitle, claimTitleLabel, claimTitle, claimDescriptionLabel, claimDescription, characterCountLabel, claimAmountLabel, claimAmount, receivedBankingInfoLabel, receivedBankingInfo);

        // Styling for labels
        claimTitleLabel.getStyleClass().add("claim-form-label");
        claimDescriptionLabel.getStyleClass().add("claim-form-label");
        claimAmountLabel.getStyleClass().add("claim-form-label");
        receivedBankingInfoLabel.getStyleClass().add("claim-form-label");

        // Styling for TextFields
        claimTitle.setPadding(new Insets(8,16,8,16));
        claimDescription.setPadding(new Insets(8,16,8,16));
        claimAmount.setPadding(new Insets(8,16,8,16));
        receivedBankingInfo.setPadding(new Insets(8,16,8,16));

        Text rightTitle = new Text("Feature Images");
        rightTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 18));

        ImageUploadForm imageUploadForm = new ImageUploadForm();

        // Create a VBox for the right column
        VBox rightColumn = new VBox();
        rightColumn.setSpacing(10);
        rightColumn.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(16), Insets.EMPTY)));
        gridPane.add(rightColumn, 1, 0);
        rightColumn.maxHeightProperty().bind(imageUploadForm.getUploadImagePane().heightProperty());
        rightColumn.setPadding(new Insets(16, 24, 16, 24));

        rightColumn.getChildren().addAll(rightTitle, imageUploadForm);

        // Set the vertical alignment of the rightColumn to TOP
        GridPane.setValignment(rightColumn, VPos.TOP);

        // Add an action to the Clear Button
        // Add an action to the Clear Button
        clearButton.setOnAction(e -> {
            // Check if all the fields are already empty
            if (claimTitle.getText().isEmpty() && claimDescription.getText().isEmpty() && claimAmount.getText().isEmpty() && receivedBankingInfo.getText().isEmpty() && imageUploadForm.getSelectedFiles().isEmpty()) {
                // If they are, exit the action
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Clear Form");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to clear the form?");

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStyleClass().add("dialog-pane");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Clear all the fields
                    claimTitle.clear();
                    claimDescription.clear();
                    claimAmount.clear();
                    receivedBankingInfo.clear();
                    imageUploadForm.clearSelectedFiles();
                }
            });
        });

        // Add an action to the Submit Button
        submitButton.setOnAction(e -> {
            List<String> missingFields = new ArrayList<>();

            String claimTitleValidation = validateTextField(claimTitle, "claim title");
            String claimDescriptionValidation = validateTextField(claimDescription, "claim description");
            String claimAmountValidation = validateTextField(claimAmount, "claim amount");
            String receivedBankingInfoValidation = validateTextField(receivedBankingInfo, "received banking info");


            if (claimTitle.getText().trim().isEmpty()) {
                missingFields.add("Claim Title");
            }
            if (claimDescription.getText().trim().isEmpty()) {
                missingFields.add("Claim Description");
            }
            if (claimAmount.getText().trim().isEmpty()) {
                missingFields.add("Claim Amount");
            }
            if (receivedBankingInfo.getText().trim().isEmpty()) {
                missingFields.add("Received Banking Info");
            }

            if (!missingFields.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Missing Information");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in the following fields: " + String.join(", ", missingFields));

                alert.showAndWait();
            }

            if (claimTitleValidation == null && claimDescriptionValidation == null && claimAmountValidation == null && receivedBankingInfoValidation == null) {
                // Generate a random claim ID
                Random random = new Random();
                String claimId = "f-" + String.format("%010d", random.nextInt(1_000_000_000));

                // Temporary random card number
                String cardNumber = String.format("%010d", random.nextInt(1_000_000_000));

                // Get the current date
                LocalDate claimDate = LocalDate.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String claimFormattedDate = claimDate.format(dateFormatter);

                // Set the status to NEW
                Status status = Status.NEW;

                // Print the claim information
                System.out.println("Claim ID: " + claimId);
                System.out.println("Claim Date: " + claimFormattedDate);
                System.out.println("Card Number: " + cardNumber);
                System.out.println("Status: " + status);
                System.out.println("Claim Title: " + claimTitle.getText());
                System.out.println("Claim Description: " + claimDescription.getText());
                System.out.println("Claim Amount: " + claimAmount.getText());
                System.out.println("Received Banking Info: " + receivedBankingInfo.getText());

                // Print the names of the selected image files
                List<File> selectedFiles = imageUploadForm.getSelectedFiles();
                if (!selectedFiles.isEmpty()) {
                    System.out.println("Uploaded Images:");
                    for (File file : selectedFiles) {
                        System.out.println(file.getName());
                    }
                } else {
                    System.out.println("No images were uploaded.");
                }

                // Clear all the fields
                claimTitle.clear();
                claimDescription.clear();
                claimAmount.clear();
                receivedBankingInfo.clear();
                imageUploadForm.clearSelectedFiles();

                // Show a notification
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Claim Submission");
                alert.setHeaderText(null);
                alert.setContentText("Claim has been submitted successfully!");

                alert.showAndWait();

            } else {

                if (claimTitleValidation != null) {
                    System.out.println(claimTitleValidation);
                }
                if (claimDescriptionValidation != null) {
                    System.out.println(claimDescriptionValidation);
                }
                if (claimAmountValidation != null) {
                    System.out.println(claimAmountValidation);
                }
                if (receivedBankingInfoValidation != null) {
                    System.out.println(receivedBankingInfoValidation);
                }
            }
        });

    }

    private String validateTextField(TextField textField, String fieldName) {
        if (textField.getText().trim().isEmpty()) {
            textField.setStyle("-fx-border-color: red;");
            return "Please enter the " + fieldName + "!";
        } else {
            textField.setStyle("");
            return null;
        }
    }


}
