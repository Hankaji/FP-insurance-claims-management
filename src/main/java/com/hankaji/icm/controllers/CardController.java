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
        cardListView.setCellFactory(loadAll());
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
            String hql = "select c.cardNumber, c.expirationDate, c.policyOwner " +
                    "from InsuranceCard c";
            List<Object[]> results = session.createQuery(hql).list();

            List<InsuranceCard> cards = new ArrayList<>();
            for (Object[] result : results) {
                Long cardNumber = (Long) result[0];
                LocalDateTime expirationDate = (LocalDateTime) result[1];
                PolicyOwner policyOwner = (PolicyOwner) result[2]; // Cast to PolicyOwner

                InsuranceCard card = new InsuranceCard();
                card.setCardNumber(cardNumber);
                card.setExpirationDate(expirationDate);
                card.setPolicyOwner(policyOwner); // Set the PolicyOwner object

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
                        private final HBox detailsHBox = new HBox(); // Use HBox for horizontal layout

                        @Override
                        protected void updateItem(InsuranceCard card, boolean empty) {
                            super.updateItem(card, empty);
                            if (empty || card == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                // Create the cell content here
                                Button downArrowButton = new Button("\u25BE"); // Unicode character for down arrow
                                downArrowButton.getStyleClass().add("fp-button-icon");
                                downArrowButton.setFont(Font.font(12)); // Set font size for the arrow

                                VBox cardNumberLabel = createLabel("Card Number:", card.getCardNumber().toString(), 150);
                                VBox expirationDateLabel = createLabel("Expiration Date:", card.getExpirationDate().toString(), 150);
                                VBox policyOwnerLabel = createLabel("Policy Owner:", card.getPolicyOwner().getName(), 150);

                                // Create the "three vertical dots" button
                                Button dotsButton = new Button("\u22EE"); // Unicode character for vertical ellipsis
                                dotsButton.getStyleClass().add("fp-button-icon");
                                dotsButton.setAlignment(Pos.CENTER);
                                dotsButton.setFont(Font.font(12));
                                dotsButton.setOnAction(event -> {
                                    // Create a context menu for the actions
                                    ContextMenu contextMenu = new ContextMenu();

                                    // Delete item
                                    MenuItem deleteItem = new MenuItem("Delete");
                                    deleteItem.setOnAction(e -> {
                                        // Handle deletion action here
                                        deleteCard(card);
                                    });

                                    contextMenu.getItems().addAll(deleteItem);
                                    contextMenu.show(dotsButton, Side.RIGHT, 0, 0);
                                });

                                Region spacer = new Region();
                                HBox.setHgrow(spacer, Priority.ALWAYS);

                                // Create HBox to hold labels and buttons
                                HBox cellContainer = new HBox(10);
                                cellContainer.getStyleClass().add("fp-cell-container"); // Add a custom style class
                                cellContainer.setAlignment(Pos.CENTER_LEFT);
                                cellContainer.getChildren().addAll(downArrowButton, cardNumberLabel, expirationDateLabel, policyOwnerLabel, spacer, dotsButton);

                                // Style the HBox for main information
                                cellContainer.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");
                                cellContainer.setPadding(new Insets(10));

                                setGraphic(cellContainer);
                            }
                        }

                        // Helper method to create a label with fixed width
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