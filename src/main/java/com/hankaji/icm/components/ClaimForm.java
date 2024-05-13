package com.hankaji.icm.components;

import com.hankaji.icm.models.Claim;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClaimForm extends VBox {

    protected List<Claim> claims = new ArrayList<>();
    private Claim currentClaim = null;

    public enum Status {
        NEW,
        PROCESSING,
        DONE
    }

    public void displayLastClaim() {
        if (!claims.isEmpty()) {
            Claim lastClaim = claims.getLast(); // Get the last claim in the list

            System.out.println("Claim ID: " + lastClaim.getId());
            System.out.println("Claim Date: " + lastClaim.getClaimDate());
            System.out.println("Card Number: " + lastClaim.getCardNumber());
            System.out.println("Status: " + lastClaim.getStatus());
            System.out.println("Claim Title: " + lastClaim.getClaimTitle());
            System.out.println("Claim Description: " + lastClaim.getClaimDescription());
            System.out.println("Claim Amount: " + lastClaim.getClaimAmount());
            System.out.println("Received Banking Info: " + lastClaim.getReceiverBankingInfo());

            // Print the names of the selected image files
            List<File> selectedFiles = lastClaim.getImageFiles();
            if (!selectedFiles.isEmpty()) {
                System.out.println("Uploaded Images:");
                for (File file : selectedFiles) {
                    System.out.println(file.getName());
                }
            } else {
                System.out.println("No images were uploaded.");
            }
        } else {
            System.out.println("No claims have been submitted yet.");
        }
    }

    public ClaimForm() {
        // Create a GridPane
        GridPane gridPane = new GridPane();

        // Set the gridPane properties
        gridPane.setHgap(8);
        gridPane.setVgap(8);

        // Create an HBox for the title and the Save button

        // Create the Form Title and Styling
        Label formTitle = new Label("Add new claim");
        formTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 32));

        // Set the column constraints for the GridPane
        ColumnConstraints column1Constraint = new ColumnConstraints();
        column1Constraint.setPercentWidth(60);
        gridPane.getColumnConstraints().add(column1Constraint);

        ColumnConstraints column2Constraint = new ColumnConstraints();
        column2Constraint.setPercentWidth(40);
        gridPane.getColumnConstraints().add(column2Constraint);

        // Create a VBox for the left column
        VBox leftColumn = new VBox();
        leftColumn.setSpacing(10);
        gridPane.add(leftColumn, 0, 0);
        leftColumn.setPadding(new Insets(24));

        Text leftTitle = new Text("Details");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));

        FormTextField claimTitleField = new FormTextField("Claim Title", "Ex: Car Accident Claim");
        TextField claimTitleTF = claimTitleField.getFormField();

        // Validate the claim title to only letters and spaces and ignore
        // non-alphabetical input
        claimTitleTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z\\s]*$")) {
                claimTitleTF.setText(newValue.replaceAll("[^a-zA-Z\\s]", ""));
            }
        });

        VBox claimDescriptionField = _claimDescriptionField();
        TextArea claimDescriptionTF = ((FPTextArea) claimDescriptionField.getChildren().get(0)).getFormTextArea();

        // Label of the claim amount
        FormTextField claimAmountField= new FormTextField("Claim Amount (in $)", "Ex: 1000");
        TextField claimAmount = claimAmountField.getFormField();

        // Validate the claim amount and ignore non-numeric input
        claimAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                claimAmount.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // // Label of the received banking info
        // Label receivedBankingInfoLabel = new Label("Received Banking Info");

        // // Create TextField for the received banking info
        // TextField receivedBankingInfo = new TextField();
        // receivedBankingInfo.setPromptText("Ex: ACB-John Doe-1234567890");
        FormTextField receivedBankingInfoField = new FormTextField("Received Banking Info", "Ex: ACB-John Doe-1234567890");
        TextField receivedBankingInfo = receivedBankingInfoField.getFormField();

        // Add the components to the left column and styling
        leftColumn.getChildren().addAll(
                leftTitle,
                claimTitleField,
                claimDescriptionField,
                claimAmountField,
                receivedBankingInfoField);

        Text rightTitle = new Text("Feature Images");
        rightTitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 18));

        ImageUploadForm imageUploadForm = new ImageUploadForm();

        // Create a VBox for the right column
        VBox rightColumn = new VBox();
        rightColumn.setSpacing(10);
        gridPane.add(rightColumn, 1, 0);
        rightColumn.setPadding(new Insets(16));
        rightColumn.getChildren().addAll(rightTitle, imageUploadForm);

        // Set the vertical alignment of the rightColumn to TOP
        GridPane.setValignment(rightColumn, VPos.TOP);

        // Create a Clear Button
        MFXButton clearButton = new MFXButton("Clear");
        clearButton.getStyleClass().add("clear-button");
        HBox.setHgrow(clearButton, Priority.ALWAYS);

        // Create a Submit Button
        MFXButton submitButton = new MFXButton("Submit");
        submitButton.getStyleClass().add("submit-button");
        HBox.setHgrow(submitButton, Priority.ALWAYS);

        // Create the Update Button Object
        MFXButton updateButton = new MFXButton("Edit");
        updateButton.getStyleClass().add("update-button");
        HBox.setHgrow(updateButton, Priority.ALWAYS);

        // Create an HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setMaxWidth(Double.MAX_VALUE);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(12);
        buttonBox.getChildren().addAll(clearButton, submitButton);

        // Add the GridPane to the VBox
        getChildren().addAll(gridPane, buttonBox);

        // Add an action to the Clear Button
        clearButton.setOnAction(e -> {
            // Check if all the fields are already empty
            if (claimTitleTF.getText().isEmpty() && claimDescriptionTF.getText().isEmpty()
                    && claimAmount.getText().isEmpty() && receivedBankingInfo.getText().isEmpty()
                    && imageUploadForm.getSelectedFiles().isEmpty()) {
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
                    claimTitleTF.clear();
                    claimDescriptionTF.clear();
                    claimAmount.clear();
                    receivedBankingInfo.clear();
                    imageUploadForm.clearSelectedFiles();
                }
            });
        });


        // Add an action to the Submit Button
        submitButton.setOnAction(e -> {
            List<String> missingFields = new ArrayList<>();

            String claimTitleValidation = validateTextField(claimTitleTF, "claim title");
            String claimDescriptionValidation = validateTextArea(claimDescriptionTF);
            String claimAmountValidation = validateTextField(claimAmount, "claim amount");
            String receivedBankingInfoValidation = validateTextField(receivedBankingInfo, "received banking info");

            if (claimTitleTF.getText().trim().isEmpty()) {
                missingFields.add("Claim Title");
            }
            if (claimDescriptionTF.getText().trim().isEmpty()) {
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

            if (claimTitleValidation == null && claimDescriptionValidation == null && claimAmountValidation == null
                    && receivedBankingInfoValidation == null) {
                // Generate a random claim ID
                Random random = new Random();
                String claimId = "f-" + String.format("%010d", random.nextInt(1_000_000_000));

                // Temporary random card number
                String cardNumber = String.format("%010d", random.nextInt(1_000_000_000));

                // Get the current date
                LocalDateTime claimDate = LocalDateTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String claimFormattedDate = claimDate.format(dateFormatter);

                // Set the status to NEW
//                Status status = Status.NEW;
                Claim.Status status = Claim.Status.NEW;

                // Create a new claim object
                Claim claim = new Claim(claimId, claimDate, claimTitleTF.getText(), cardNumber, claimDate,
                        new ArrayList<>(), Integer.parseInt(claimAmount.getText()), status, receivedBankingInfo.getText(), claimTitleTF.getText(), claimDescriptionTF.getText(), imageUploadForm.getSelectedFiles());

                // Add the claim to the list of claims
                claims.add(claim);

                // Set the current claim to the last claim in the list
                currentClaim = claim;

                System.out.println(currentClaim);

//                System.out.println(claim);

                claims.forEach(System.out::println);

                System.out.println(claims.size() + " claims have been submitted.");
                displayLastClaim();


                if (currentClaim == null) {

                    claims.add(claim);
                    currentClaim = claim;

                } else {

                    // Find the claim with the same ID
                    Claim existingClaim = claims.stream().filter(c -> c.getId().equals(currentClaim.getId())).findFirst().orElse(null);

                    if (existingClaim != null) {

                        existingClaim.setCardNumber(currentClaim.getCardNumber());
                        existingClaim.setClaimAmount(currentClaim.getClaimAmount());
                        existingClaim.setClaimDate(currentClaim.getClaimDate());
                        existingClaim.setClaimDescription(currentClaim.getClaimDescription());
                        existingClaim.setClaimTitle(currentClaim.getClaimTitle());
                        existingClaim.setImageFiles(currentClaim.getImageFiles());
                        existingClaim.setReceiverBankingInfo(currentClaim.getReceiverBankingInfo());
                        existingClaim.setStatus(currentClaim.getStatus());

                    }
                }

                // Show a notification
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Claim Submission");
                alert.setHeaderText(null);
                alert.setContentText("Claim has been submitted successfully!");

                alert.showAndWait();

                clearButton.setDisable(true);
                clearButton.setVisible(false);
                submitButton.setDisable(true);
                submitButton.setVisible(false);
                buttonBox.getChildren().add(updateButton);


                // Make the fields uneditable
                claimTitleTF.setEditable(false);
                claimDescriptionTF.setEditable(false);
                claimAmount.setEditable(false);
                receivedBankingInfo.setEditable(false);
                imageUploadForm.setRemoveButtonVisible(false);

                // Disable the Upload Image button
                imageUploadForm.getUploadImagePane().getChildren().remove(imageUploadForm.getUploadImageButton());

            } else {

                if (claimTitleValidation != null) {
                    System.out.println(claimTitleValidation);
                }
                if (claimAmountValidation != null) {
                    System.out.println(claimAmountValidation);
                }
                if (receivedBankingInfoValidation != null) {
                    System.out.println(receivedBankingInfoValidation);
                }
            }
        });

        updateButton.setOnAction(e -> {
            // Set the current claim to the last claim in the list
            currentClaim = claims.getLast();

            // Make the fields editable
            claimTitleTF.setEditable(true);
            claimDescriptionTF.setEditable(true);
            claimAmount.setEditable(true);
            receivedBankingInfo.setEditable(true);
            imageUploadForm.setRemoveButtonVisible(true);

            // Hide the Update button and show the Clear and Submit buttons
            clearButton.setVisible(true);
            clearButton.setDisable(false);
            submitButton.setVisible(true);
            submitButton.setDisable(false);

            buttonBox.getChildren().remove(updateButton);

            // Enable the Upload Image button
            imageUploadForm.getUploadImagePane().getChildren().add(imageUploadForm.getUploadImageButton());
        });

    }

    private VBox _claimDescriptionField() {
        VBox container = new VBox();
        FPTextArea claimDescriptionField = new FPTextArea(
                "Claim Description",
                "Ex: I was involved in a car accident on 12/12/2021");
        TextArea claimDescriptionTF = claimDescriptionField.getFormTextArea();
        claimDescriptionTF.setPrefHeight(100);

        // Set a maximum character limit
        final int maxCharacterCount = 400; // Set the maximum character count
        claimDescriptionTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxCharacterCount) {
                claimDescriptionTF.setText(oldValue);
            }
        });

        // Create a label to display the used characters out of the total allowable
        // characters
        Label characterCountLabel = new Label();
        characterCountLabel.textProperty()
                .bind(Bindings.createStringBinding(
                        () -> claimDescriptionTF.getText().length() + "/" + maxCharacterCount,
                        claimDescriptionTF.textProperty()));
        characterCountLabel.setMaxWidth(Double.MAX_VALUE);
        characterCountLabel.setAlignment(Pos.CENTER_RIGHT);
        
        container.getChildren().addAll(claimDescriptionField, characterCountLabel);
        return container;
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

    private String validateTextArea(TextArea textArea) {
        if (textArea.getText().trim().isEmpty()) {
            textArea.setStyle("-fx-border-color: red;");
            return "Please enter the " + "claim description" + "!";
        } else {
            textArea.setStyle("");
            return null;
        }
    }

}
