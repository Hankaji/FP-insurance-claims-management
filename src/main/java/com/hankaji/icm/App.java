package com.hankaji.icm;

import com.hankaji.icm.database.SessionManager;

/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: JavaFX, MaterialFX
*/

import com.hankaji.icm.views.AddClaimPage;
import com.hankaji.icm.views.CustomerDashboard;
import com.hankaji.icm.views.LogIn;

import com.hankaji.icm.views.SignUpPage;
import com.hankaji.icm.views.components.CardDetails;
import com.hankaji.icm.views.components.ClaimForm;
import com.hankaji.icm.controllers.ClaimController;
import com.hankaji.icm.controllers.CustomerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Instantiate the CustomerController
        CustomerController customerController = new CustomerController();

        Parent page = FXMLLoader.load(getClass().getResource("/fxml/RootView.fxml"));

        Scene scene = new Scene(page);

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