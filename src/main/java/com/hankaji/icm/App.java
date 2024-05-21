package com.hankaji.icm;


import java.util.List;

import com.hankaji.icm.controllers.ViewDependentController;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.views.LogIn;

/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: JavaFX, MaterialFX
*/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the CardView.fxml and get the root node
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/YourClaimView.fxml"));
        // Parent root = loader.load();

        // Parent page = FXMLLoader.load(getClass().getResource("/fxml/RootView.fxml"));
        LogIn logIn = new LogIn();

        Scene scene = new Scene(logIn);

        // Set up the stage
        stage.setTitle("Insurance Customer Management System");
        stage.setScene(scene);
        stage.setMinWidth(1500);
        stage.setMinHeight(800);
        stage.show();

        // Check if the user is already logged in
        logIn.getController().checkLoginStatus(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}