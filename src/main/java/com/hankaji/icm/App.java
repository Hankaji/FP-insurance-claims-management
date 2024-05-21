package com.hankaji.icm;


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
        // Instantiate the CustomerController

        // Parent page = FXMLLoader.load(getClass().getResource("/fxml/RootView.fxml"));
        LogIn logIn = new LogIn();

        Scene scene = new Scene(logIn);
        
        // Set up the stage
        stage.setTitle("Insurance Customer Management System");
        stage.setScene(scene);
        stage.setMinWidth(1600);
        stage.setMinHeight(900);
        stage.show();

        // Check if the user is already logged in
        logIn.getController().checkLoginStatus(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}