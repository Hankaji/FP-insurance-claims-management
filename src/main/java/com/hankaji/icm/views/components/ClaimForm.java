package com.hankaji.icm.views.components;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        // Add the GridPane to the VBox
        getChildren().add(gridPane);

        // Set the VBox properties
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(8, 16, 8, 16));

        // Create the Form Title and Styling
        Label formTitle = new Label("Claim Form");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
        gridPane.add(formTitle, 1,0);

        // Label of the claim title
        Label claimTitleLabel = new Label("Claim Title:");

        // Create TextField for the claim title
        TextField claimTitle = new TextField();
        claimTitle.setPromptText("Ex: Car Accident Claim");

        // Label of the claim description
        Label claimDescriptionLabel = new Label("Claim Description:");

        // Create TextField for the claim description
        TextField claimDescription = new TextField();
        claimDescription.setPromptText("Ex: I was involved in a car accident on 12/12/2021");

        // Label of the claim amount
        Label claimAmountLabel = new Label("Claim Amount:");

        // Create TextField for the claim amount
        TextField claimAmount = new TextField();
        claimAmount.setPromptText("Ex: 1000");

        // Label of the received banking info
        Label receivedBankingInfoLabel = new Label("Received Banking Info:");

        // Create TextField for the received banking info
        TextField receivedBankingInfo = new TextField();
        receivedBankingInfo.setPromptText("Ex: ACB-John Doe-1234567890");

        // Create an ImageUploadForm Label
        Label imageUploadFormLabel = new Label("Upload Image:");

        // Create an ImageUploadForm Field
        ImageUploadForm imageUploadForm = new ImageUploadForm();

        // Create a Submit Button
        MFXButton submitButton = new MFXButton("Submit");

        // Add an action to the Submit Button
        submitButton.setOnAction(e -> {
            String claimTitleValidation = validateTextField(claimTitle, "claim title");
            String claimDescriptionValidation = validateTextField(claimDescription, "claim description");
            String claimAmountValidation = validateTextField(claimAmount, "claim amount");
            String receivedBankingInfoValidation = validateTextField(receivedBankingInfo, "received banking info");

            if (claimTitleValidation == null && claimDescriptionValidation == null && claimAmountValidation == null && receivedBankingInfoValidation == null) {
                // Generate a random claim ID
                Random random = new Random();
                String claimId = "f-" + String.format("%010d", random.nextInt(1_000_000_000));

                // Get the current date
                LocalDate claimDate = LocalDate.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String claimFormattedDate = claimDate.format(dateFormatter);

                // Set the status to NEW
                Status status = Status.NEW;

                // Print the claim information
                System.out.println("Claim ID: " + claimId);
                System.out.println("Claim Date: " + claimFormattedDate);
                System.out.println("Status: " + status);
                System.out.println("Claim Title: " + claimTitle.getText());
                System.out.println("Claim Description: " + claimDescription.getText());
                System.out.println("Claim Amount: " + claimAmount.getText());
                System.out.println("Received Banking Info: " + receivedBankingInfo.getText());

                for (File file : imageUploadForm.getSelectedFiles()) {
                    System.out.println("Selected file: " + file.getName());
                }

                // Clear all the fields
                claimTitle.clear();
                claimDescription.clear();
                claimAmount.clear();
                receivedBankingInfo.clear();

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

        // Add the Labels and TextFields to the GridPane
        gridPane.add(claimTitleLabel, 0, 1);
        gridPane.add(claimTitle, 1, 1);
        GridPane.setColumnSpan(claimTitle, 2);

        gridPane.add(claimDescriptionLabel, 0, 2);
        gridPane.add(claimDescription, 1, 2);
        GridPane.setColumnSpan(claimDescription, 2);

        gridPane.add(claimAmountLabel, 0, 3);
        gridPane.add(claimAmount, 1, 3);
        GridPane.setColumnSpan(claimAmount, 2);

        gridPane.add(receivedBankingInfoLabel, 0, 4);
        gridPane.add(receivedBankingInfo, 1, 4);
        GridPane.setColumnSpan(receivedBankingInfo, 2);

        gridPane.add(imageUploadFormLabel, 0, 5);
        gridPane.add(imageUploadForm, 1, 5);
        GridPane.setColumnSpan(imageUploadForm, 2);

        gridPane.add(submitButton, 1, 6);

        // Styling for labels
        claimTitleLabel.getStyleClass().add("claim-form-label");
        claimDescriptionLabel.getStyleClass().add("claim-form-label");
        claimAmountLabel.getStyleClass().add("claim-form-label");
        receivedBankingInfoLabel.getStyleClass().add("claim-form-label");
        imageUploadFormLabel.getStyleClass().add("claim-form-label");

        // Styling for TextFields
        claimTitle.getStyleClass().add("claim-form-textfield");
        claimDescription.getStyleClass().add("claim-form-textfield");
        claimAmount.getStyleClass().add("claim-form-textfield");
        receivedBankingInfo.getStyleClass().add("claim-form-textfield");

        // Styling for the Submit Button
        submitButton.getStyleClass().add("submit-button");

        // Set the GridPane column constraints
        setHalignment(claimTitleLabel, HPos.LEFT);
        setHalignment(claimDescriptionLabel, HPos.LEFT);
        setHalignment(claimAmountLabel, HPos.LEFT);
        setHalignment(receivedBankingInfoLabel, HPos.LEFT);
        setHalignment(submitButton, HPos.LEFT);
        setHalignment(imageUploadFormLabel, HPos.LEFT);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(column1);

        // Set the GridPane row constraints
        setValignment(imageUploadFormLabel, VPos.TOP);

        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(row);
        }

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
