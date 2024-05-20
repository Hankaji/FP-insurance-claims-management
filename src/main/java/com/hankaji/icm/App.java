package com.hankaji.icm;


import com.hankaji.icm.views.LogIn;

/**
* @author <Hoang Thai Phuc - s3978081>
* @version 1.0
*
* Libraries used: JavaFX, MaterialFX
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Instantiate the CustomerController

        Parent page = FXMLLoader.load(getClass().getResource("/fxml/RootView.fxml"));

        Scene scene = new Scene(new LogIn());

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