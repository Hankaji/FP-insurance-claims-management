package com.hankaji.icm.views;

import com.hankaji.icm.views.components.CardDetails;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class CustomerDashboard extends TilePane {
    public CustomerDashboard() {
        getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        getStyleClass().add("customer-dashboard");

        setPadding(new Insets(24));

        CardDetails cardDetails = new CardDetails();
        
        getChildren().add(cardDetails);
    }
}
