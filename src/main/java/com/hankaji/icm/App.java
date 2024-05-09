package com.hankaji.icm;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: JavaFX, MaterialFX
*/

import com.hankaji.icm.views.LogInPage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The head Application program of the project
 *
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        Scene scene = new Scene(new LogInPage(), 1024, 580);

        stage.setTitle("Insurance Claim Management System");
        stage.setScene(scene);
        stage.show();
    }
    public static void main( String[] args ) {
        launch();
    }
}
