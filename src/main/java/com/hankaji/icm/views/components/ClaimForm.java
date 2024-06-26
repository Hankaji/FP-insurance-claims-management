package com.hankaji.icm.views.components;

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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.components.FPTextArea;
import com.hankaji.icm.components.FPTextField;
import com.hankaji.icm.controllers.AddClaimController;
import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.ID;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;

public class ClaimForm extends VBox {

    SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    AddClaimController controller = new AddClaimController();

    public ClaimForm() {
        // Create a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);

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

        FPTextField claimTitleField = new FPTextField("Claim Title", "Ex: Car Accident Claim");
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
        FPTextField claimAmountField = new FPTextField("Claim Amount (in $)", "Ex: 1000");
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
        FPTextField receivedBankingInfoField = new FPTextField("Received Banking Info", "Ex: ACB-John Doe-1234567890");
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

        // Create an HBox for the buttons
        HBox buttonBox = new HBox();
        buttonBox.setMaxWidth(Double.MAX_VALUE);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(12);
        buttonBox.setPadding(new Insets(16));
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
            // String claimDescriptionValidation = validateTextField(claimDescriptionTF,
            // "claim description");
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

            if (claimTitleValidation == null && claimAmountValidation == null
                    && receivedBankingInfoValidation == null) {
                controller.addClaim(claimTitleTF, claimDescriptionTF, claimAmount, receivedBankingInfo, imageUploadForm);

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

}
