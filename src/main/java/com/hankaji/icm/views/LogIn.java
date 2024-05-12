package com.hankaji.icm.views;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LogIn extends StackPane {

    public LogIn() {
        VBox loginForm = new VBox();
        loginForm.setStyle("-fx-background-color: white;");

        ImageView logo = new ImageView("https://img.freepik.com/free-psd/gradient-abstract-logo_23-2150689652.jpg?t=st=1715495040~exp=1715495640~hmac=6782b898b57a64d09058feda379b6bb79ea1f24749feed2b7071e72dd5114a32");

        loginForm.getChildren().add(logo);
        getChildren().add(loginForm);
    }

}
