package com.hankaji.icm;

import com.hankaji.icm.controllers.AdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminView.fxml"));
            Parent root = loader.load();
            AdminController controller = loader.getController();
            controller.setStage(primaryStage);

            primaryStage.setTitle("Admin Page");
            primaryStage.setScene(new Scene(root, 1024, 580));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}