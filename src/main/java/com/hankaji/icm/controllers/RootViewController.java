package com.hankaji.icm.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.hankaji.icm.views.CustomerDashboard;
import com.hankaji.icm.views.components.CardDetails;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class RootViewController implements Initializable {

    @FXML private Circle avatar;

    @FXML private BorderPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/114131672/original/5f03e84975a3e52c91166d03b89c6af7e061ca44/send-you-a-random-meme-image-that-will-tickle-your-fancy.jpg");
        avatar.setFill(new ImagePattern(image));

        Node root = new CustomerDashboard();
        rootPane.setCenter(root);
    }
    
}
