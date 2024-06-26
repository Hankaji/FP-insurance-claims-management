package com.hankaji.icm.views.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class CardDetails extends VBox {
    public CardDetails(String cardNumberStr, String cardStatusStr, String cardExpiryDateStr, String totalCoverageStr) {
        getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        getStyleClass().add("card-details");

        setSpacing(6);
        setPadding(new Insets(16));
        setPrefWidth(600);


        Label cardTitle = new Label("Card Details");
        cardTitle.getStyleClass().add("card-title");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ImageView rightArrow = new ImageView(new Image(getClass().getResourceAsStream("/images/icons/right-arrow.png")));
        rightArrow.setFitWidth(16);
        rightArrow.setFitHeight(16);

        HBox header = new HBox(cardTitle, spacer, rightArrow);
        header.setAlignment(Pos.CENTER_LEFT);

        Label cardNumber = new Label("Card Number: " + cardNumberStr);
        cardNumber.getStyleClass().add("card-number");

        Circle cardStatus = new Circle(4);
        cardStatus.getStyleClass().add("card-status");

        Label cardStatusText = new Label(cardStatusStr);
        cardStatusText.getStyleClass().add("card-status-text");

        HBox cardStatusBox = new HBox(cardStatus, cardStatusText);
        cardStatusBox.setAlignment(Pos.CENTER_LEFT);
        cardStatusBox.setSpacing(4);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        Label cardExpiryDate = new Label("Expiry Date: " + cardExpiryDateStr);

        HBox extraInfo = new HBox(cardStatusBox, spacer2, cardExpiryDate);
        extraInfo.setAlignment(Pos.CENTER_LEFT);

        Region sizedBox = new Region();
        sizedBox.setPrefHeight(16);

        Label totalCoverage = new Label("Total Coverage: " + totalCoverageStr);
        totalCoverage.getStyleClass().add("total-coverage");

        getChildren().addAll(header, cardNumber, extraInfo, sizedBox, totalCoverage);
    }
}
