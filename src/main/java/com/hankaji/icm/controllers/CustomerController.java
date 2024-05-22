package com.hankaji.icm.controllers;

import com.hankaji.icm.components.Throbber;
import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.CustomerIdComparator;
import com.hankaji.icm.lib.Utils;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.customer.Customer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Callback;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CustomerController {
    private SessionManager sessionManager;

    public CustomerController() {
        sessionManager = SessionManager.getInstance();
    }

    @FXML
    private ListView<Customer> customerListView;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> sortChoiceBox;

    @FXML
    private StackPane loadingPane;

    @FXML
    private void initialize() {
        // Set the cell factory for the ListView
        customerListView.setCellFactory(new ListViewCellFactory());
        // Load all customers data when the page is initialized
        CompletableFuture.runAsync(() -> {
            Throbber throbber = new Throbber();
            loadingPane.getChildren().add(throbber);
        })
                .thenRun(this::loadAllCustomersData)
                .thenRun(() -> Platform.runLater(() -> {
                    loadingPane.getChildren().clear();
                    Utils.disable(loadingPane);
                }));
    }

    @FXML
    private void handleSearch() {
        String id = searchField.getText();
        searchCustomer(id);
    }

    @FXML
    private void handleSort() {
        String selectedOption = sortChoiceBox.getValue();
        if (selectedOption != null) {
            ObservableList<Customer> items = customerListView.getItems();
            List<Customer> itemList = new ArrayList<>(items);

            if (selectedOption.equals("Ascending")) {
                itemList.sort(new CustomerIdComparator());
            } else if (selectedOption.equals("Descending")) {
                itemList.sort(Collections.reverseOrder(new CustomerIdComparator()));
            }

            customerListView.setItems(FXCollections.observableList(itemList));
        }
    }

    private void loadAllCustomersData() {
        customerListView.getItems().clear(); // Clear existing items
        try (Session session = sessionManager.getSessionFactory().openSession()) {
            List<Customer> customers = session.createQuery("from Customer", Customer.class).list();
            ObservableList<Customer> observableList = FXCollections.observableArrayList(customers);
            customerListView.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchCustomer(String id) {
        try (Session session = sessionManager.getSessionFactory().openSession()) {
            Customer customer = session.get(Customer.class, id);
            ObservableList<Customer> items = FXCollections.observableArrayList();
            if (customer != null) {
                items.add(customer);
            }
            customerListView.setItems(items);

            if (items.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No customer found with ID: " + id);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteCustomer(Customer customer) {
        // Implement the logic to delete the customer
        try (Session session = sessionManager.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadAllCustomersData();
    }

    @FXML
    private void viewCustomerDetails(Customer customer) {
    }

    public class ListViewCellFactory implements Callback<ListView<Customer>, ListCell<Customer>> {
        @Override
        public ListCell<Customer> call(ListView<Customer> param) {
            return new ListCell<Customer>() {
                @Override
                protected void updateItem(Customer cus, boolean empty) {
                    super.updateItem(cus, empty);
                    if (empty || cus == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Create the cell content here
                        Label idLabel = createLabel(String.valueOf(cus.getId()), 200); // Set width for ID label
                        // Label cNameLabel = createLabel(cus.getUser().getFullname(), 300); // Set
                        // width for Customer Name label
                        InsuranceCard insuranceCard = cus.getInsuranceCard();
                        Label insuranceCardNumberLabel = createLabel(
                                (insuranceCard == null ? "" : insuranceCard.getCardNumber().toString()), 300);
                        Label holderIdLabel = createLabel(cus.getHolder() == null? "N/A" : cus.getHolder().getUser().getFullname(), 200); // Set width for Holder
                                                                                                 // ID label

                        // Create the "three vertical dots" button
                        Button dotsButton = new Button("\u22EE"); // Unicode character for vertical ellipsis
                        dotsButton.setOnAction(event -> {
                            // Create a context menu for the actions
                            ContextMenu contextMenu = new ContextMenu();

                            // Delete item
                            MenuItem deleteItem = new MenuItem("Delete");
                            deleteItem.setOnAction(e -> {
                                // Handle deletion action here
                                deleteCustomer(cus);
                            });

                            // View details item
                            MenuItem viewDetailsItem = new MenuItem("View Details");
                            viewDetailsItem.setOnAction(e -> {
                                // Handle view details action here
                                viewCustomerDetails(cus);
                            });

                            contextMenu.getItems().addAll(deleteItem, viewDetailsItem);
                            contextMenu.show(dotsButton, Side.RIGHT, 0, 0);
                        });

                        // Create HBoxes for each label with different spacing
                        HBox idHBox = new HBox();
                        idHBox.getChildren().add(idLabel);

                        HBox insuranceCardNumberHBox = new HBox();
                        insuranceCardNumberHBox.getChildren().add(insuranceCardNumberLabel);
                        insuranceCardNumberHBox.setPadding(new Insets(0, 50, 0, 0)); // Add right padding for gap
                                                                                     // between ID and Insurance Card
                                                                                     // Number

                        HBox holderIdHBox = new HBox();
                        holderIdHBox.getChildren().add(holderIdLabel);
                        holderIdHBox.setPadding(new Insets(0, 50, 0, 0)); // Add right padding for gap between Insurance
                                                                          // Card Number and Holder ID

                        // Create a parent HBox to hold the label HBoxes and the dotsButton
                        HBox hbox = new HBox(20); // Adjust overall spacing between HBoxes
                        hbox.getChildren().addAll(idHBox, insuranceCardNumberHBox, holderIdHBox, dotsButton);
                        hbox.setPadding(new Insets(5));
                        hbox.setStyle(
                                "-fx-background-color: #f0f0f0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");

                        setGraphic(hbox);
                    }
                }

                // Helper method to create a label with specified width
                private Label createLabel(String text, double width) {
                    Label label = new Label(text == null ? "N/A" : text);
                    label.setPrefWidth(width);
                    label.setStyle("-fx-text-fill: black;");
                    return label;
                }
            };
        }
    }

    public AnchorPane getRoot() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CustomerView.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}