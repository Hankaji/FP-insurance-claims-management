package com.hankaji.icm.views;

import com.hankaji.icm.components.FPComboBox;
import com.hankaji.icm.components.FPPasswordField;
import com.hankaji.icm.components.FPTextField;
import com.hankaji.icm.controllers.SignUpController;
import com.hankaji.icm.errors.UserExistsException;
import com.hankaji.icm.lib.EnumStringConverter;
import com.hankaji.icm.models.User;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hibernate.persister.entity.EntityNameUse.UseKind;

public class SignUpPage extends StackPane {

    SignUpController controller = new SignUpController();

    public SignUpPage() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm());

        VBox signUpFormContainer = signUpForm();

        // Add the root VBox to a StackPane
        StackPane signUpPageStackPane = new StackPane();
        signUpPageStackPane.getChildren().add(signUpFormContainer);

        // Add the StackPane to the main StackPane
        getChildren().add(signUpPageStackPane);

    }

    private VBox signUpForm() {
        // Create a VBox to hold all the elements
        VBox rootContainer = new VBox();
        rootContainer.setAlignment(Pos.CENTER);
        rootContainer.setSpacing(16);
        rootContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(16), Insets.EMPTY)));

        // Add some shadow effects to the VBox
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.5));
        rootContainer.setEffect(shadow);

        // Set the padding of the VBox to be responsive to the window size
        rootContainer.paddingProperty().bind(widthProperty().multiply(0.05)
                .map(w -> new Insets(w.doubleValue() * 0.5, w.doubleValue(), w.doubleValue() * 0.5, w.doubleValue())));

        // Set the background image for the StackPane
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/bg.jpg")));
        BackgroundSize bgSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, bgSize);
        setBackground(new Background(bg));

        // Set the padding of the StackPane to be responsive to the window size
        paddingProperty().bind(widthProperty().multiply(0.3)
                .map(w -> new Insets(w.doubleValue(), w.doubleValue(), w.doubleValue(), w.doubleValue())));

        // Add the page image to the top of the VBox
        Image appImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/app-logo.png")));
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

        // Create a LabelAndTextField object for fullName
        FPTextField fullNameTextField = new FPTextField("Full name", "Enter Your Full Name");

        FPTextField emailTextField = new FPTextField("Email", "Enter Your Email");

        // Create a LabelAndPasswordField object for password
        FPPasswordField fpPasswordField = new FPPasswordField("Password", "Enter Your Password");
        PasswordField passwordField = fpPasswordField.getFormField();

        // Create a LabelAndPasswordField object for re-entering the password
        FPPasswordField fpReEnterPasswordField = new FPPasswordField("Re-enter Password", "Re-enter Your Password");
        PasswordField reEnterPasswordField = fpReEnterPasswordField.getFormField();

        HBox cardProviderAndAccountType = getCardProviderAndAccountType();

        // Create a VBox to contain all the LabelAndTextFields
        VBox fieldsContainer = new VBox();
        fieldsContainer.setSpacing(12);
        fieldsContainer.prefWidthProperty().bind(rootContainer.widthProperty());
        fieldsContainer.getChildren().addAll(
                fullNameTextField,
                emailTextField,
                fpPasswordField,
                fpReEnterPasswordField,
                cardProviderAndAccountType);

        // Create the Create button
        MFXButton signUpButton = new MFXButton("Sign Up");
        signUpButton.getStyleClass().add("button");
        signUpButton.prefWidthProperty().bind(rootContainer.widthProperty());

        signUpButton.setOnAction(event -> {
            signUpAction(fullNameTextField, emailTextField, passwordField, reEnterPasswordField, event);
        });

        // Add all the elements to the root VBox
        rootContainer.getChildren().addAll(
                pageImageAndPageLabelContainer,
                fieldsContainer,
                signUpButton);
        return rootContainer;
    }

    private void signUpAction(FPTextField fullNameTextField, FPTextField emailTextField, PasswordField passwordField,
            PasswordField reEnterPasswordField, ActionEvent event) {
        // Get the fullName and password fields
        String fullName = fullNameTextField.getFormField().getText();
        String email = emailTextField.getFormField().getText();
        String password = passwordField.getText();
        String reEnteredPassword = reEnterPasswordField.getText();

        // Check if the password and re-entered password match
        if (password.equals(reEnteredPassword)) {
            System.out.println("fullName: " + fullName);
            System.out.println("Password: " + password);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Passwords do not match. Please re-enter your password.");
            alert.showAndWait();
            passwordField.clear();
            passwordField.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            reEnterPasswordField.clear();
            reEnterPasswordField.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            return;
        }

        // Check if the fullName, password, and re-entered password fields are empty
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || reEnteredPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the missing fields");
            alert.showAndWait();

            fullNameTextField.getFormField().setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            passwordField.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            reEnterPasswordField.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            return;
        }

        if (fullName.length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("fullName must be at least 8 characters long");
            alert.showAndWait();
            fullNameTextField.getFormField().clear();
            fullNameTextField.getFormField().setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            return;
        }

        // Check if the email is valid
        if (!email.contains("@") || !email.contains(".")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid email address");
            alert.showAndWait();
            emailTextField.getFormField().clear();
            emailTextField.getFormField().setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            return;
        }

        if (reEnteredPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please re-enter your password");
            alert.showAndWait();

            reEnterPasswordField.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            return;
        }

        // Define the regex pattern for the password
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            passwordField.clear();
            reEnterPasswordField.clear();
            passwordField.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            reEnterPasswordField.setBorder(new Border(
                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Password must be at least 8 characters long, include an uppercase letter, a lowercase letter and a number");
            alert.showAndWait();
            return;
        }

        // Get the card provider and account type
        @SuppressWarnings("unchecked")
        String owner = ((ComboBox<String>) this.lookup("#card-owner")).getValue();

        @SuppressWarnings("unchecked")
        User.Roles accountType = ((ComboBox<User.Roles>) this.lookup("#account-type")).getValue();

        signUp(fullName, email, password, owner, accountType, event);
    }

    private void signUp(String fullName, String email, String password, String owner, User.Roles accountType, ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);

        try {
            controller.SignUp(
                    fullName,
                    email,
                    password,
                    owner,
                    accountType);
            
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(new LogIn());
            stage.setScene(scene);
        } catch (UserExistsException e) {
            alert.setContentText("User already exists");
            alert.showAndWait();
        }
    }

    private HBox getCardProviderAndAccountType() {
        FPComboBox<String> cardProvider = new FPComboBox<String>("Card owner");
        // cardProvider.setMaxWidth(Double.MAX_VALUE);
        // cardProvider.getComboBox().setMaxWidth(Double.MAX_VALUE);
        cardProvider.getComboBox().setId("card-owner");
        cardProvider.getComboBox().getItems().addAll(controller.getAllOwnder());
        cardProvider.getComboBox().setValue("Choose owner");

        FPComboBox<User.Roles> accountType = new FPComboBox<User.Roles>("Account type");
        // accountType.setMaxWidth(Double.MAX_VALUE);
        // accountType.getComboBox().setMaxWidth(Double.MAX_VALUE);
        accountType.getComboBox().setId("account-type");
        accountType.getComboBox().getItems().addAll(
                Arrays.stream(User.Roles.values())
                        .filter(day -> day != User.Roles.ADMIN)
                        .collect(Collectors.toList()));
        accountType.getComboBox().setConverter(new EnumStringConverter<>(User.Roles.class));
        accountType.getComboBox().setValue(User.Roles.DEPENDENT);

        HBox cardProviderAndAccountType = new HBox();
        cardProviderAndAccountType.setMaxWidth(Double.MAX_VALUE);
        cardProviderAndAccountType.setSpacing(12);
        cardProviderAndAccountType.getChildren().addAll(cardProvider, accountType);
        return cardProviderAndAccountType;
    }

}