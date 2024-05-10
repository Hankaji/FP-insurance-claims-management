package com.hankaji.icm;
/** 
* @author <Hoang Thai Phuc - s3978081> 
* @version 1.0
*
* Libraries used: JavaFX, MaterialFX
*/

import com.hankaji.icm.views.DependentsHomePage;
import com.hankaji.icm.views.LogInPage;

import com.hankaji.icm.views.components.ClaimForm;
import javafx.application.Application;
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

//        Scene scene = new Scene(new LogInPage(), 640, 480);
        Scene dependentsScene = new Scene(new DependentsHomePage(), 1000, 500);
//        Scene scene = new Scene(new ClaimForm(), 640, 480);

        stage.setTitle("Insurance Claim Management System");
//        stage.setScene(scene);
        dependentsScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/styles.css")).toExternalForm());
        stage.setScene(dependentsScene);
        stage.setMinWidth(1000);
        stage.setMinHeight(500);
        System.out.println(dependentsScene.getStylesheets());
//        stage.setFullScreen(true);
        stage.show();
    }
    public static void main( String[] args ) {
        launch();
    }
}
