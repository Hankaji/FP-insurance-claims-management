package com.hankaji.icm;

import com.hankaji.icm.database.SessionManager;

/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: JavaFX, MaterialFX
*/

import com.hankaji.icm.views.DependentsHomePage;
import com.hankaji.icm.views.LogIn;

import com.hankaji.icm.views.SignUpPage;
import com.hankaji.icm.views.components.ClaimForm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The head Application program of the project
 *
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        _setUp();
        _closeSession(stage);

        // System.out.println(getClass().getResource("/fxml/RootView.fxml"));

        // Parent page = FXMLLoader.load(getClass().getResource("/fxml/RootView.fxml"));

        Scene scene = new Scene(new SignUpPage());

        stage.setTitle("Insurance Claim Management System");
        stage.setScene(scene);
        // stage.setScene(dependentsScene);
        stage.setMinWidth(1600);
        stage.setMinHeight(900);
        // System.out.println(dependentsScene.getStylesheets());
        // stage.setFullScreen(true);
        stage.show();
    }

    private void _closeSession(Stage stage) {
        // Close the session factory when the application is closed
        stage.setOnCloseRequest(e -> {
            SessionManager.getInstance().tearDown();
        });
    }

    /**
     * Set up the session factory.
     * When create new instance of the application, the session is already initialized in the constructor
     */
    public void _setUp() {
        // Initialize the session factory (Hibernate)
        SessionManager.getInstance();
    }

    public static void main(String[] args) {
        try {
            launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
