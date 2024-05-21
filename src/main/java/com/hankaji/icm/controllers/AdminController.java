package com.hankaji.icm.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AdminController {

    @FXML
    private BorderPane adminPane;

    @FXML
    private VBox mainContent;

    @FXML
    private void handleClaims() {
        navigateTo("/fxml/ClaimView.fxml");
    }

    @FXML
    private void handleCustomers() {
        navigateTo("/fxml/CustomerView.fxml");
    }

    @FXML
    private void handleDependents() {
        navigateTo("/fxml/DependentViews.fxml");
    }

    private void navigateTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            adminPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}