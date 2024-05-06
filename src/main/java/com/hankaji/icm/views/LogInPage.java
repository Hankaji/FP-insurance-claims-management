package com.hankaji.icm.views;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

public class LogInPage extends StackPane {

    public LogInPage() {
        // String javaVersion = System.getProperty("java.version");
        // String javafxVersion = System.getProperty("javafx.version");
        // Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " +
        // javaVersion + ".");
        // getChildren().add(l);

        final double CONTAINER_WIDTH = 400;

        VBox rootContainer = new VBox();
        rootContainer.setAlignment(Pos.CENTER_LEFT);
        rootContainer.setSpacing(6);
        rootContainer.setMaxWidth(CONTAINER_WIDTH);

        Label header = new Label("Log in");

        MFXTextField usernameField = new MFXTextField("", "Enter your username", "Username");
        usernameField.setPrefWidth(CONTAINER_WIDTH * 0.8);

        MFXPasswordField passwordField = new MFXPasswordField("", "Enter your password", "Password");
        passwordField.setPrefWidth(CONTAINER_WIDTH * 0.8);

        MFXCheckbox rememberMe = new MFXCheckbox("Remember me");
        // rememberMe.setPrefWidth(320 * 0.8);

        Label tos = new Label("By logging in, you agree to our Terms of Service and Privacy Policy.");
        tos.setTextFill(Paint.valueOf("#888888"));

        MFXButton loginButton = new MFXButton("Log in");
        loginButton.setButtonType(ButtonType.RAISED);

        Label signUp = new Label("Don't have an account? Sign up");
        signUp.setPrefWidth(CONTAINER_WIDTH);
        signUp.setTextAlignment(TextAlignment.CENTER);

        rootContainer.getChildren().addAll(
                header,
                usernameField,
                passwordField,
                rememberMe,
                tos,
                loginButton,
                signUp);

        getChildren().add(rootContainer);
        // setWidth(640);
    }

}
