package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.models.InsuranceCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    private SessionFactory sessionFactory;

    public CardController() {
        sessionFactory = SessionManager.getInstance().getSessionFactory();
    }

    @FXML
    private ListView<InsuranceCard> cardListView;

    @FXML
    private TextField searchField;

    @FXML
    private Button addCard;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardListView.setCellFactory(new ListViewCellFactory());
        addCard.setOnAction(this::addCard);
        loadAllCardsData();
    }

    private void addCard(ActionEvent e) {
        // This should open a new view or form for adding a new card
        BorderPane rootPane = (BorderPane) addCard.getScene().lookup("#RootView");
        try {
            VBox addCardForm = FXMLLoader.load(getClass().getResource("/fxml/AddCardView.fxml"));
            rootPane.setCenter(addCardForm);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleSearch() {
        searchCard(searchField.getText());
    }

    private void loadAllCardsData() {
        try (Session session = sessionFactory.openSession()) {
            List<InsuranceCard> cards = session.createQuery("from InsuranceCard", InsuranceCard.class).list();
            ObservableList<InsuranceCard> observableList = FXCollections.observableArrayList(cards);
            cardListView.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchCard(String cardNumber) {
        try (Session session = sessionFactory.openSession()) {
            InsuranceCard card = session.get(InsuranceCard.class, Long.valueOf(cardNumber));
            ObservableList<InsuranceCard> items = FXCollections.observableArrayList();
            if (card != null) {
                items.add(card);
            }
            cardListView.setItems(items);

            if (items.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No card found with number: " + cardNumber);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ListViewCellFactory implements Callback<ListView<InsuranceCard>, ListCell<InsuranceCard>> {
        @Override
        public ListCell<InsuranceCard> call(ListView<InsuranceCard> param) {
            return new ListCell<InsuranceCard>() {
                @Override
                protected void updateItem(InsuranceCard card, boolean empty) {
                    super.updateItem(card, empty);
                    if (empty || card == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(10);
                        hbox.getChildren().addAll(
                                createLabel("Card Number: " + card.getCardNumber()),
                                createLabel("Expiration Date: " + card.getExpirationDate().toLocalDate().toString())
                        );
                        setGraphic(hbox);
                    }
                }

                private Label createLabel(String text) {
                    Label label = new Label(text);
                    label.setFont(Font.font(14));
                    return label;
                }
            };
        }
    }
}