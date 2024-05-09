package com.hankaji.icm.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class RootViewController implements Initializable {

    @FXML private Circle avatar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/114131672/original/5f03e84975a3e52c91166d03b89c6af7e061ca44/send-you-a-random-meme-image-that-will-tickle-your-fancy.jpg");
        avatar.setFill(new ImagePattern(image));
    }
    
}
