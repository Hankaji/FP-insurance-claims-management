package com.hankaji.icm.views;

import com.hankaji.icm.components.LabelAndPasswordField;
import com.hankaji.icm.components.LabelAndTextField;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class SignUpPage extends StackPane {

    public SignUpPage() {
        // Create a VBox to hold all the elements
        VBox rootContainer = new VBox();
        rootContainer.setAlignment(Pos.CENTER);
        rootContainer.setSpacing(20);
        rootContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(16), Insets.EMPTY)));

        // Set the padding of the VBox to be responsive to the window size
        rootContainer.paddingProperty().bind(widthProperty().multiply(0.06).map(w -> new Insets(w.doubleValue() * 0.5, w.doubleValue(), w.doubleValue() * 0.5, w.doubleValue())));

        // Set the background image for the StackPane
        Image bgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/bgImage.jpg")));
        BackgroundSize bgSize = new BackgroundSize(1.0, 1.0, true, true, false, false);
        BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bgSize);
        setBackground(new Background(bg));

        // Set the padding of the StackPane to be responsive to the window size
        paddingProperty().bind(widthProperty().multiply(0.25).map(w -> new Insets(w.doubleValue(), w.doubleValue(), w.doubleValue(), w.doubleValue())));

        // Add the page image to the top of the VBox
        Image appImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/appImage.png")));
        ImageView signUpImageView = new ImageView(appImage);
        signUpImageView.setFitWidth(200);
        signUpImageView.setFitHeight(200);

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

        // Create a LabelAndPasswordField object for re-entering the password
        LabelAndPasswordField reEnterPasswordField = new LabelAndPasswordField(new Label("Re-enter Password"), new PasswordField());

        // Create a VBox to contain all the LabelAndTextFields
        VBox fieldsContainer = new VBox();
        fieldsContainer.setSpacing(20);
        fieldsContainer.prefWidthProperty().bind(rootContainer.widthProperty());
        fieldsContainer.getChildren().addAll(usernameField, passwordField, reEnterPasswordField);

        // Create the Create button
        MFXButton signUpButton = new MFXButton("Sign Up");
        signUpButton.getStyleClass().add("sign-up-button");
        signUpButton.prefWidthProperty().bind(rootContainer.widthProperty());

        rootContainer.getChildren().addAll(pageImageAndPageLabelContainer, fieldsContainer, signUpButton);

        StackPane signUpPageStackPane = new StackPane();
        signUpPageStackPane.getChildren().add(rootContainer);

        getChildren().add(signUpPageStackPane);

    }

}