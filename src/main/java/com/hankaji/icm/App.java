package com.hankaji.icm;

import com.hankaji.icm.controllers.ClaimController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ClaimController claimController = new ClaimController();
        Scene scene = new Scene(claimController.getRoot(), 945, 550);

        stage.setTitle("Insurance Claim Management System");
        stage.setScene(scene);
        stage.setMinWidth(945);
        stage.setMinHeight(550);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}