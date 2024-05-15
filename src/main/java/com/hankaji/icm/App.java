package com.hankaji.icm;

import com.hankaji.icm.views.ClaimController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ClaimController claimController = new ClaimController();
        Scene scene = new Scene(claimController.getRoot(), 1000, 550);

        stage.setTitle("Insurance Claim Management System");
        stage.setScene(scene);
        stage.setMinWidth(1050);
        stage.setMinHeight(550);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}