package com.hankaji.icm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the CardView.fxml and get the root node
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CardView.fxml"));
        Parent root = loader.load();

        // Create a scene with the loaded root node
        Scene scene = new Scene(root);

        // Set up the stage
        stage.setTitle("Insurance Customer Management System");
        stage.setScene(scene);
        stage.setMinWidth(1500);
        stage.setMinHeight(800);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}