package com.hankaji.icm.views;

import com.hankaji.icm.components.LabelAndPasswordField;
import com.hankaji.icm.components.LabelAndTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPage extends StackPane {

    public SignUpPage() {
        // Create a VBox to hold all the elements
        VBox rootContainer = new VBox();
        rootContainer.setAlignment(Pos.CENTER);
        rootContainer.setSpacing(20);
        rootContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(16), Insets.EMPTY)));

        // Add some shadow effects to the VBox
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));
        rootContainer.setEffect(shadow);

        // Set the padding of the VBox to be responsive to the window size
        rootContainer.paddingProperty().bind(widthProperty().multiply(0.05).map(w -> new Insets(w.doubleValue() * 0.5, w.doubleValue(), w.doubleValue() * 0.5, w.doubleValue())));

        // Set the background image for the StackPane
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/bgImage.jpg")));
        BackgroundSize bgSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
        setBackground(new Background(bg));

        // Set the padding of the StackPane to be responsive to the window size
        paddingProperty().bind(widthProperty().multiply(0.3).map(w -> new Insets(w.doubleValue(), w.doubleValue(), w.doubleValue(), w.doubleValue())));

        // Add the page image to the top of the VBox
        Image appImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/appImage.png")));
        ImageView signUpImageView = new ImageView(appImage);
        signUpImageView.setFitWidth(80);
        signUpImageView.setFitHeight(80);

        // Create a Text below the page image
        Label signUpPageLabel = new Label("Sign Up");
        signUpPageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Add the page image and text to a separate VBox
        VBox pageImageAndPageLabelContainer = new VBox();
        pageImageAndPageLabelContainer.setAlignment(Pos.CENTER);
        pageImageAndPageLabelContainer.setSpacing(4);
        pageImageAndPageLabelContainer.getChildren().addAll(signUpImageView, signUpPageLabel);

        // Create a LabelAndTextField object for username
        LabelAndTextField usernameField = new LabelAndTextField(new Label("Username"), new TextField());

        // Create a LabelAndPasswordField object for password
        LabelAndPasswordField passwordField = new LabelAndPasswordField(new Label("Password"), new PasswordField());
        passwordField.getPasswordField().setPromptText("Enter Your Password");

        // Create a LabelAndPasswordField object for re-entering the password
        LabelAndPasswordField reEnterPasswordField = new LabelAndPasswordField(new Label("Re-enter Password"), new PasswordField());
        reEnterPasswordField.getPasswordField().setPromptText("Re-enter Your Password");

        // Create a VBox to contain all the LabelAndTextFields
        VBox fieldsContainer = new VBox();
        fieldsContainer.setSpacing(10);
        fieldsContainer.prefWidthProperty().bind(rootContainer.widthProperty());
        fieldsContainer.getChildren().addAll(usernameField, passwordField, reEnterPasswordField);

        // Create the Create button
        MFXButton signUpButton = new MFXButton("Sign Up");
        signUpButton.getStyleClass().add("sign-up-button");
        signUpButton.prefWidthProperty().bind(rootContainer.widthProperty());

        signUpButton.setOnAction(event -> {

            // Get the username and password fields
            String username = ((TextField) usernameField.getChildren().get(1)).getText();
            String password = passwordField.getPasswordField().getText();
            String reEnteredPassword = reEnterPasswordField.getPasswordField().getText();

            // Check if the password and re-entered password match
            if (!username.isEmpty() & password.equals(reEnteredPassword)) {
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match. Please re-enter your password.");
                alert.showAndWait();
                passwordField.getPasswordField().clear();
                passwordField.getPasswordField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                reEnterPasswordField.getPasswordField().clear();
                reEnterPasswordField.getPasswordField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }

            if (username.isEmpty() & password.isEmpty() & reEnteredPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter the missing fields");
                alert.showAndWait();

                usernameField.getTextField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                passwordField.getPasswordField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                reEnterPasswordField.getPasswordField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            }

             if (username.length() < 8) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username must be at least 8 characters long");
                alert.showAndWait();
                usernameField.getTextField().clear();
                usernameField.getTextField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }

            if (reEnteredPassword.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please re-enter your password");
                alert.showAndWait();

                reEnterPasswordField.getPasswordField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }

            // Define the regex pattern for the password
            String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
            Pattern pattern = Pattern.compile(passwordPattern);
            Matcher matcher = pattern.matcher(password);

            if (!matcher.matches()) {
                passwordField.getPasswordField().clear();
                reEnterPasswordField.getPasswordField().clear();
                passwordField.getPasswordField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                reEnterPasswordField.getPasswordField().setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a number, and a special character.");
                alert.showAndWait();
            }

        });

        // Add all the elements to the root VBox
        rootContainer.getChildren().addAll(pageImageAndPageLabelContainer, fieldsContainer, signUpButton);

        // Add the root VBox to a StackPane
        StackPane signUpPageStackPane = new StackPane();
        signUpPageStackPane.getChildren().add(rootContainer);

        // Add the StackPane to the main StackPane
        getChildren().add(signUpPageStackPane);

    }

}