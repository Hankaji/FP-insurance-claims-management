package com.hankaji.icm;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: JavaFX, MaterialFX
*/

import com.hankaji.icm.views.DependentsHomePage;
import com.hankaji.icm.views.LogIn;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The head Application program of the project
 *
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // System.out.println(getClass().getResource("/fxml/RootView.fxml"));

        // Parent page = FXMLLoader.load(getClass().getResource("/fxml/RootView.fxml"));
        
        Scene scene = new Scene(new LogIn(), 1024, 580);

        stage.setTitle("Insurance Claim Management System");
//        stage.setScene(scene);
        stage.setScene(dependentsScene);
        stage.setMinWidth(1000);
        stage.setMinHeight(500);
//        stage.setFullScreen(true);
        stage.show();
    }
    public static void main( String[] args ) {
        try {
            launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
