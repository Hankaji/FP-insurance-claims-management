package com.hankaji.icm;

import com.hankaji.icm.controllers.ClaimController;
import com.hankaji.icm.controllers.CustomerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Instantiate the CustomerController
        CustomerController customerController = new CustomerController();

        // Create a scene using the root node of the CustomerController
        Scene scene = new Scene(customerController.getRoot(), 945, 550);

        // Set up the stage
        stage.setTitle("Insurance Customer Management System");
        stage.setScene(scene);
        stage.setMinWidth(945);
        stage.setMinHeight(550);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}