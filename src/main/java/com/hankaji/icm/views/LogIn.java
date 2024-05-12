package com.hankaji.icm.views;

import java.util.Objects;

import com.hankaji.icm.components.FormTextField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LogIn extends StackPane {

    HBox centerLayout;

    public LogIn() {
        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/style.css")).toExternalForm());
        getStyleClass().add("authorization-container");

        // Component to center the login form
        centerLayout = new HBox();
        centerLayout.setFillHeight(false);
        centerLayout.setStyle("-fx-background-color: transparent;");
        centerLayout.setAlignment(Pos.CENTER);

        // Login form
        VBox loginForm = _loginForm();

        // Add components to the login form
        centerLayout.getChildren().add(loginForm);

        getChildren().add(centerLayout);
    }

    private final VBox _loginForm() {
        VBox loginForm = new VBox();
        loginForm.setSpacing(12);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.prefWidthProperty().bind(centerLayout.widthProperty().multiply(0.4));
        loginForm.setEffect(new DropShadow(10.0, Color.rgb(0, 0, 0, 0.2)));
        loginForm.getStyleClass().add("login-form");
        // loginForm.setStyle("-fx-background-color: white;");

        ImageView logo = new ImageView("/images/app-logo.png");
        logo.setFitWidth(80);
        logo.setFitHeight(80);

        Label title = new Label("Welcome back, Login");
        title.getStyleClass().add("login-title");

        FormTextField username = new FormTextField("Username", "Enter your username");
        FormTextField password = new FormTextField("Password", "Enter your password");

        VBox loginButtonContainer = new VBox();
        loginButtonContainer.setSpacing(8);

        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.getStyleClass().add("login-button");

        Label signUp = new Label("Don't have an account? Sign up");

        loginButtonContainer.getChildren().addAll(loginButton, signUp);

        loginForm.getChildren().addAll(
                logo,
                title,
                username,
                password,
                loginButtonContainer);

        return loginForm;
    };

}
