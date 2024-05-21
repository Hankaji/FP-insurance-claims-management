package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.customer.PolicyOwner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private ChoiceBox<String> sortChoiceBox;

    @FXML
    private Button filterButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the sorting options
        sortChoiceBox.setItems(FXCollections.observableArrayList("Ascending", "Descending"));
        // Set the default selection
        sortChoiceBox.getSelectionModel().selectFirst();

        // Add action listener to the choice box for sorting
        sortChoiceBox.setOnAction(event -> handleSort(null));
        cardListView.setCellFactory(loadAll());

        sortChoiceBox.setItems(FXCollections.observableArrayList("Ascending", "Descending"));
        filterButton.setOnAction(this::handleSort); // Set action for filter button

        loadAllCardsData();
    }

    @FXML
    private void handleSort(ActionEvent event) {
        String selectedOption = sortChoiceBox.getValue();
        if (selectedOption != null) {
            ObservableList<InsuranceCard> items = cardListView.getItems();
            List<InsuranceCard> itemList = new ArrayList<>(items);

            if (selectedOption.equals("Ascending")) {
                itemList.sort(Comparator.comparingLong(InsuranceCard::getCardNumber));
            } else if (selectedOption.equals("Descending")) {
                itemList.sort(Comparator.comparingLong(InsuranceCard::getCardNumber).reversed());
            }

            cardListView.setItems(FXCollections.observableList(itemList));
        }
    }

    @FXML
    private void handleSearch() {
        searchCard(searchField.getText());
    }

    private void loadAllCardsData() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select c.cardNumber, c.expirationDate, c.policyOwner " +
                    "from InsuranceCard c";
            List<Object[]> results = session.createQuery(hql).list();

            List<InsuranceCard> cards = new ArrayList<>();
            for (Object[] result : results) {
                Long cardNumber = (Long) result[0];
                LocalDateTime expirationDate = (LocalDateTime) result[1];
                PolicyOwner policyOwner = (PolicyOwner) result[2];

                InsuranceCard card = new InsuranceCard();
                card.setCardNumber(cardNumber);
                card.setExpirationDate(expirationDate);
                card.setPolicyOwner(policyOwner);

                cards.add(card);
            }

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

    private void deleteCard(InsuranceCard card) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(card);
            transaction.commit();
            cardListView.getItems().remove(card);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Callback<ListView<InsuranceCard>, ListCell<InsuranceCard>> loadAll() {
        return new Callback<ListView<InsuranceCard>, ListCell<InsuranceCard>>() {
            @Override
            public ListCell<InsuranceCard> call(ListView<InsuranceCard> param) {
                return new ListCell<InsuranceCard>() {
                    private final HBox detailsHBox = new HBox();

                    @Override
                    protected void updateItem(InsuranceCard card, boolean empty) {
                        super.updateItem(card, empty);
                        if (empty || card == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            VBox cardNumberLabel = createLabel("Card Number:", card.getCardNumber().toString(), 150);
                            VBox expirationDateLabel = createLabel("Expiration Date:", card.getExpirationDate().toString(), 150);
                            VBox policyOwnerLabel = createLabel("Policy Owner:", card.getPolicyOwner().getName(), 150);

                            Button dotsButton = new Button("\u22EE");
                            dotsButton.getStyleClass().add("fp-button-icon");
                            dotsButton.setAlignment(Pos.CENTER);
                            dotsButton.setFont(Font.font(12));
                            dotsButton.setOnAction(event -> {
                                ContextMenu contextMenu = new ContextMenu();

                                MenuItem deleteItem = new MenuItem("Delete");
                                deleteItem.setOnAction(e -> deleteCard(card));

                                contextMenu.getItems().addAll(deleteItem);
                                contextMenu.show(dotsButton, Side.RIGHT, 0, 0);
                            });

                            Region spacer = new Region();
                            HBox.setHgrow(spacer, Priority.ALWAYS);

                            HBox cellContainer = new HBox(10);
                            cellContainer.getStyleClass().add("fp-cell-container");
                            cellContainer.setAlignment(Pos.CENTER_LEFT);
                            cellContainer.getChildren().addAll(cardNumberLabel, expirationDateLabel, policyOwnerLabel, spacer, dotsButton);

                            cellContainer.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");
                            cellContainer.setPadding(new Insets(10));

                            setGraphic(cellContainer);
                        }
                    }

                    private VBox createLabel(String category, String text, double width) {
                        VBox container = new VBox(4);

                        Label cateLabel = new Label(category);
                        cateLabel.setFont(Font.font(10));
                        cateLabel.setTextFill(Color.web("#0f0f0f"));
                        cateLabel.setPrefWidth(width);
                        cateLabel.setMaxWidth(Region.USE_PREF_SIZE);

                        Label value = new Label(text);
                        value.setPrefWidth(width);
                        value.setMaxWidth(Region.USE_PREF_SIZE);

                        container.getChildren().addAll(cateLabel, value);
                        return container;
                    }
                };
            }
        };
    }

    public AnchorPane getRoot() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CardView.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}