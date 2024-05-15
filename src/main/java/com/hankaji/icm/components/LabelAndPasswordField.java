package com.hankaji.icm.components;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class LabelAndPasswordField extends VBox{

    private final PasswordField passwordField;
    private final TextField visiblePasswordField;

    public LabelAndPasswordField(Label label, PasswordField passwordField) {
        this.passwordField = passwordField;
        visiblePasswordField = new TextField();
        visiblePasswordField.getStyleClass().add("custom-text-field");

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(4);

        label.getStyleClass().add("field-label");

        passwordField.setFocusTraversable(false);
        passwordField.getStyleClass().add("custom-password-field");

        Image eyeOpened = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/visibility.png")));
        Image eyeClosed = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/visibility-off.png")));

        ImageView eyeClosedIcon = new ImageView(eyeClosed);
        eyeClosedIcon.setFitWidth(20);
        eyeClosedIcon.setFitHeight(20);
        eyeClosedIcon.setCursor(Cursor.HAND);

        ImageView eyeOpenedIcon = new ImageView(eyeOpened);
        eyeOpenedIcon.setFitWidth(20);
        eyeOpenedIcon.setFitHeight(20);
        eyeOpenedIcon.setCursor(Cursor.HAND);

        HBox passwordFieldAndEyeIconContainer = new HBox();
        passwordFieldAndEyeIconContainer.setSpacing(4);
        passwordFieldAndEyeIconContainer.setAlignment(Pos.CENTER);

        passwordFieldAndEyeIconContainer.getChildren().addAll(passwordField, eyeClosedIcon);
        HBox.setHgrow(passwordField, Priority.ALWAYS);
        HBox.setHgrow(visiblePasswordField, Priority.ALWAYS);

        getChildren().addAll(label, passwordFieldAndEyeIconContainer);

        // Add event handlers to the visibility icons
        eyeClosedIcon.setOnMouseClicked(event -> {
            passwordFieldAndEyeIconContainer.getChildren().removeAll(passwordField, eyeClosedIcon);
            visiblePasswordField.setText(passwordField.getText());
            passwordFieldAndEyeIconContainer.getChildren().addAll(visiblePasswordField, eyeOpenedIcon);
        });

        eyeOpenedIcon.setOnMouseClicked(event -> {
            passwordFieldAndEyeIconContainer.getChildren().removeAll(visiblePasswordField, eyeOpenedIcon);
            passwordField.setText(visiblePasswordField.getText());
            passwordFieldAndEyeIconContainer.getChildren().addAll(passwordField, eyeClosedIcon);
        });
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

}