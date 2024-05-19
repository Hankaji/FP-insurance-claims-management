package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.ClaimIdComparator;
import com.hankaji.icm.lib.Utils;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.views.AddClaimPage;

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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


public class ClaimController implements Initializable {
    private SessionFactory sessionFactory;

    public ClaimController() {
        sessionFactory = SessionManager.getInstance().getSessionFactory();
    }

    @FXML
    private ListView<Claim> claimListView;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> sortChoiceBox; // ChoiceBox for sorting options

    @FXML
    private Button addClaim;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set the cell factory for the ListView
        claimListView.setCellFactory(new ListViewCellFactory());

        // Initialize the sorting options
        sortChoiceBox.setItems(FXCollections.observableArrayList("Ascending", "Descending"));

        // Add action listener to the choice box for sorting
        sortChoiceBox.setOnAction(event -> handleSort());

        addClaim.setOnAction(e -> addClaim(e));

        // Load all claims data
        loadAllClaimsData();
    }

    private void addClaim(ActionEvent e) {
        BorderPane rootPane = (BorderPane) addClaim.getScene().lookup("#RootView");
        rootPane.setCenter(new AddClaimPage());
    }

    private void handleSort() {
        String selectedOption = sortChoiceBox.getValue();
        if (selectedOption != null) {
            ObservableList<Claim> items = claimListView.getItems();
            List<Claim> itemList = new ArrayList<>(items);

            if (selectedOption.equals("Ascending")) {
                itemList.sort(new ClaimIdComparator());
            } else if (selectedOption.equals("Descending")) {
                itemList.sort(Collections.reverseOrder(new ClaimIdComparator()));
            }

            claimListView.setItems(FXCollections.observableList(itemList));
        }
    }

    @FXML
    private void handleViewAll() {
        loadAllClaimsData();
    }

    @FXML
    private void handleSearch() {
        searchClaim(searchField.getText());
    }

    private void loadAllClaimsData() {
        try (Session session = sessionFactory.openSession()) {
            List<Claim> Claim = session.createQuery("from Claim", Claim.class).list();
            ObservableList<Claim> observableList = FXCollections.observableArrayList(Claim);
            claimListView.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchClaim(String id) {
        try (Session session = sessionFactory.openSession()) {
            Claim Claim = session.get(Claim.class, id);
            ObservableList<Claim> items = FXCollections.observableArrayList();
            if (Claim != null) {
                items.add(Claim);
            }
            claimListView.setItems(items);

            if (items.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No claim found with ID: " + id);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteClaim(Claim Claim) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(Claim);
            transaction.commit();
            claimListView.getItems().remove(Claim);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ListViewCellFactory implements Callback<ListView<Claim>, ListCell<Claim>> {
        @Override
        public ListCell<Claim> call(ListView<Claim> param) {
            return new ListCell<Claim>() {
                private final HBox detailsHBox = new HBox(); // Use HBox for horizontal layout
                

                @Override
                protected void updateItem(Claim item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Create the cell content here
                        Button downArrowButton = new Button("\u25BE"); // Unicode character for down arrow
                        downArrowButton.getStyleClass().add("fp-button-icon");
                        // downArrowButton.setPrefSize(16, 16); 
                        downArrowButton.setFont(Font.font(12)); // Set font size for the arrow
                        downArrowButton.setOnAction(event -> toggleDetails(downArrowButton));

                        VBox idLabel = createLabel("Id:", item.getId(), 150); // Adjust width for ID
                        VBox insuredPersonLabel = createLabel("Insured person:", item.getInsured_person_id(), 150);
                        VBox cardNumberLabel = createLabel("Card number:", item.getCard_number().toString(), 150);
                        VBox statusLabel = createStatusLabel("Status:", item.getStatus(), 150);

                        // Create the "three vertical dots" button
                        Button dotsButton = new Button("\u22EE"); // Unicode character for vertical ellipsis
                        dotsButton.getStyleClass().add("fp-button-icon");
                        dotsButton.setAlignment(Pos.CENTER);
                        // dotsButton.setPrefSize(16, 16);
                        downArrowButton.setFont(Font.font(12));
                        dotsButton.setOnAction(event -> {
                            // Create a context menu for the actions
                            ContextMenu contextMenu = new ContextMenu();

                            // Delete item
                            MenuItem deleteItem = new MenuItem("Delete");
                            deleteItem.setOnAction(e -> {
                                // Handle deletion action here
                                deleteClaim(item);
                            });

                            // Update item
                            MenuItem updateItem = new MenuItem("Update");
                            updateItem.setOnAction(e -> {
                                // Handle update action here
                                // Implement your update logic
                            });

                            contextMenu.getItems().addAll(deleteItem, updateItem);
                            contextMenu.show(dotsButton, Side.RIGHT, 0, 0);
                        });

                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS);

                        // Create HBox to hold labels and buttons
                        HBox cellContainer = new HBox(10);
                        cellContainer.getStyleClass().add("fp-cell-container"); // Add a custom style class
                        cellContainer.setAlignment(Pos.CENTER_LEFT);
                        cellContainer.getChildren().addAll(downArrowButton, idLabel, insuredPersonLabel, cardNumberLabel, statusLabel, spacer, dotsButton);

                        // Style the HBox for main information
                        cellContainer.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");
                        cellContainer.setPadding(new Insets(10));

                        // Create BorderPane to hold HBox and HBox for details
                        BorderPane borderPane = new BorderPane();
                        borderPane.setPadding(new Insets(5));
                        borderPane.setCenter(cellContainer);
                        borderPane.setBottom(detailsHBox);
                        borderPane.getStyleClass().add("fp-cell-container"); // Add a custom style class
                        borderPane.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");

                        // Update text color based on selection
                        if (isSelected()) {
                            idLabel.setStyle("-fx-text-fill: blue;");
                            insuredPersonLabel.setStyle("-fx-text-fill: blue;");
                            cardNumberLabel.setStyle("-fx-text-fill: blue;");
                            statusLabel.setStyle("-fx-text-fill: blue;");
                            setStyle("-fx-background-color: #d0d0d0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");
                        } else {
                            idLabel.setStyle("-fx-text-fill: black;");
                            insuredPersonLabel.setStyle("-fx-text-fill: black;");
                            cardNumberLabel.setStyle("-fx-text-fill: black;");
                            statusLabel.setStyle("-fx-text-fill: black;");
                            setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");
                        }

                        setGraphic(borderPane);
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

                // Helper method to create a label with fixed width
                private VBox createStatusLabel(String category, Claim.Status status, double width) {
                    String statusStr = Utils.capitalize(status.toString(), "_");
                    VBox container = createLabel(category, statusStr.trim(), width);

                    // Add a custom style class for the status label
                    Label value = (Label) container.getChildren().get(1);
                    String styleClass = "status-" + statusStr.toLowerCase();
                    value.getStyleClass().add(styleClass);
                    // value.getStyleClass().add("fp-button");


                    return container;
                }

                // Method to show/hide additional claim details upon clicking the down-arrow button
                private void toggleDetails(Button downArrowButton) {
                    if (detailsHBox.getChildren().isEmpty()) {
                        Claim Claim = getItem();
                        if (Claim != null) {
                            // Define a date formatter
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");

                            // Format the dates
                            String formattedClaimDate = Claim.getClaim_date().toLocalDateTime().format(formatter);
                            String formattedExamDate = Claim.getExam_date().toLocalDateTime().format(formatter);

                            // Create labels with formatted dates
                            Label claimDateLabel = new Label("Claim Date: " + formattedClaimDate);
                            Label examDateLabel = new Label("Exam Date: " + formattedExamDate);
                            Label amountLabel = new Label("Claim Amount: $" + Claim.getClaim_amount());
                            Label bankingInfoLabel = new Label("Receiver Banking Info: " + Claim.getReceiver_banking_info());

                            detailsHBox.getChildren().addAll(claimDateLabel, examDateLabel, amountLabel, bankingInfoLabel);
                            detailsHBox.setSpacing(80); // Add spacing between details
                            detailsHBox.setStyle("-fx-background-color: #d0d0d0; -fx-background-radius: 10px; -fx-padding: 10;"); // Style for details section

                            // Ensure text color remains black for details
                            claimDateLabel.setStyle("-fx-text-fill: black;");
                            examDateLabel.setStyle("-fx-text-fill: black;");
                            amountLabel.setStyle("-fx-text-fill: black;");
                            bankingInfoLabel.setStyle("-fx-text-fill: black;");

                            downArrowButton.setText("\u25B4"); // Change the button text to up arrow
                        }
                    } else {
                        detailsHBox.getChildren().clear();
                        downArrowButton.setText("\u25BE"); // Change the button text back to down arrow
                    }
                }
            };
        }
    }

    public AnchorPane getRoot() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ClaimView.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}