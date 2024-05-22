package com.hankaji.icm.views;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.hankaji.icm.components.FPPasswordField;
import com.hankaji.icm.components.FPTextField;
import com.hankaji.icm.components.Throbber;
import com.hankaji.icm.controllers.LogInController;
import com.hankaji.icm.lib.Utils;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LogIn extends StackPane {

    HBox centerLayout;
    private LogInController controller = new LogInController();

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

        FPTextField email = new FPTextField("Email", "Enter your email");
        email.getFormField().setId("email-field");

        FPPasswordField password = new FPPasswordField("Password", "Enter your password");
        password.getFormField().setId("password-field");

        VBox loginButtonContainer = new VBox();
        loginButtonContainer.setSpacing(8);

        HBox loginButtonAndThrobber = new HBox();
        loginButtonAndThrobber.setSpacing(8);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(loginButton, Priority.ALWAYS);
        
        Throbber throbber = new Throbber();
        MFXProgressSpinner spinner = throbber.getSpinner();
        spinner.setMaxSize(24, 24);
        Utils.disable(throbber);

        loginButton.setOnAction(e -> {
            CompletableFuture.runAsync(() -> {
                Utils.enable(throbber);
                try {
                    controller.handleLoginButton(e, email.getFormField().getText(), password.getFormField().getText());
                } catch (InterruptedException | ExecutionException e1) {
                    e1.printStackTrace();
                }
            }).thenAccept(v -> {
                Utils.disable(throbber);
            });
        });

        loginButtonAndThrobber.getChildren().addAll(loginButton, throbber);

        HBox signUpContainer = new HBox();
        signUpContainer.setAlignment(Pos.CENTER_LEFT);
        signUpContainer.setSpacing(8);

        Label signUp = new Label("Don't have an account?");
        signUp.getStyleClass().add("sign-up-label");

        Label signUpLink = new Label("Sign up");
        signUpLink.getStyleClass().add("sign-up-label-link");
        signUpLink.setOnMouseClicked(controller::switchToSignup);

        signUpContainer.getChildren().addAll(signUp, signUpLink);

        loginButtonContainer.getChildren().addAll(loginButtonAndThrobber, signUpContainer);

        loginForm.getChildren().addAll(
                logo,
                title,
                email,
                password,
                loginButtonContainer);

        return loginForm;
    }

    public LogInController getController() {
        return controller;
    };

    

}
