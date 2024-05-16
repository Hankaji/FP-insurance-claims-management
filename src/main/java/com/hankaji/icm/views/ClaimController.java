package com.hankaji.icm.views;

import com.hankaji.icm.claim.Claim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ClaimController {

    @FXML
    private ListView<Claim> claimListView;

    @FXML
    private TextField searchField;

    @FXML
    private ChoiceBox<String> sortChoiceBox; // ChoiceBox for sorting options

    @FXML
    private void initialize() {
        // Set the cell factory for the ListView
        claimListView.setCellFactory(new ListViewCellFactory());

        // Initialize the sorting options
        sortChoiceBox.setItems(FXCollections.observableArrayList("Ascending", "Descending"));

        // Add action listener to the choice box for sorting
        sortChoiceBox.setOnAction(event -> handleSort());
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
        try {
            String content = Files.readString(Paths.get("data/default/Claim.json"));
            JSONArray jsonArray = new JSONArray(content);

            List<Claim> claims = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss", Locale.ENGLISH);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LocalDateTime claimDate = LocalDateTime.parse(jsonObject.getString("claimDate"), formatter);
                LocalDateTime examDate = LocalDateTime.parse(jsonObject.getString("examDate"), formatter);

                JSONArray documentsArray = jsonObject.getJSONArray("documents");
                List<String> documents = new ArrayList<>();
                for (int j = 0; j < documentsArray.length(); j++) {
                    documents.add(documentsArray.getString(j));
                }

                Claim claim = new Claim(
                        jsonObject.getString("id"),
                        claimDate,
                        jsonObject.getString("insuredPerson"),
                        jsonObject.getString("cardNumber"),
                        examDate,
                        (ArrayList<String>) documents,
                        jsonObject.getInt("claimAmount"),
                        Claim.Status.valueOf(jsonObject.getString("status")),
                        jsonObject.getString("receiverBankingInfo")
                );
                claims.add(claim);
            }

            ObservableList<Claim> observableList = FXCollections.observableArrayList(claims);
            claimListView.setItems(observableList);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void searchClaim(String id) {
        try {
            String content = Files.readString(Paths.get("data/default/Claim.json"));
            JSONArray jsonArray = new JSONArray(content);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss", Locale.ENGLISH);
            ObservableList<Claim> items = FXCollections.observableArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("id").equals(id)) {
                    LocalDateTime claimDate = LocalDateTime.parse(jsonObject.getString("claimDate"), formatter);
                    LocalDateTime examDate = LocalDateTime.parse(jsonObject.getString("examDate"), formatter);

                    JSONArray documentsArray = jsonObject.getJSONArray("documents");
                    List<String> documents = new ArrayList<>();
                    for (int j = 0; j < documentsArray.length(); j++) {
                        documents.add(documentsArray.getString(j));
                    }

                    Claim claim = new Claim(
                            jsonObject.getString("id"),
                            claimDate,
                            jsonObject.getString("insuredPerson"),
                            jsonObject.getString("cardNumber"),
                            examDate,
                            (ArrayList<String>) documents,
                            jsonObject.getInt("claimAmount"),
                            Claim.Status.valueOf(jsonObject.getString("status")),
                            jsonObject.getString("receiverBankingInfo")
                    );
                    items.add(claim);
                    break;
                }
            }

            claimListView.setItems(items);

            if (items.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No claim found with ID: " + id);
                alert.showAndWait();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteClaim(Claim claim) {
        ObservableList<Claim> items = claimListView.getItems();
        if (items.contains(claim)) {
            items.remove(claim);

            try {
                Path filePath = Paths.get("data/default/Claim.json");
                String content = Files.readString(filePath);
                JSONArray jsonArray = new JSONArray(content);

                JSONArray updatedArray = new JSONArray();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (!jsonObject.getString("id").equals(claim.getId())) {
                        updatedArray.put(jsonObject);
                    }
                }

                Files.write(filePath, Collections.singletonList(updatedArray.toString(2)), StandardCharsets.UTF_8);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Claim not found.");
            alert.showAndWait();
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
                        downArrowButton.setFont(Font.font(12)); // Set font size for the arrow
                        downArrowButton.setOnAction(event -> toggleDetails(downArrowButton));

                        Label blankLabel = createLabel("", 15);
                        Label idLabel = createLabel(item.getId(), 200); // Adjust width for ID
                        Label insuredPersonLabel = createLabel(item.getInsuredPerson(), 200);
                        Label cardNumberLabel = createLabel(item.getCardNumber(), 200);
                        Label statusLabel = createLabel(item.getStatus().toString(), 150);

                        // Create the "three vertical dots" button
                        Button dotsButton = new Button("\u22EE"); // Unicode character for vertical ellipsis
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

                        // Create HBox to hold labels and buttons
                        HBox hbox = new HBox(10);
                        hbox.getChildren().addAll(downArrowButton, idLabel, insuredPersonLabel, cardNumberLabel, statusLabel, dotsButton);

                        // Style the HBox for main information
                        hbox.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");
                        hbox.setPadding(new Insets(10));

                        // Create BorderPane to hold HBox and HBox for details
                        BorderPane borderPane = new BorderPane();
                        borderPane.setPadding(new Insets(5));
                        borderPane.setCenter(hbox);
                        borderPane.setBottom(detailsHBox);
                        borderPane.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10px; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");

                        // Update text color based on selection
                        if (isSelected()) {
                            idLabel.setStyle("-fx-text-fill: blue;");
                            insuredPersonLabel.setStyle("-fx-text-fill: blue;");
                            cardNumberLabel.setStyle("-fx-text-fill: blue;");
                            statusLabel.setStyle("-fx-text-fill: blue;");
                        } else {
                            idLabel.setStyle("-fx-text-fill: black;");
                            insuredPersonLabel.setStyle("-fx-text-fill: black;");
                            cardNumberLabel.setStyle("-fx-text-fill: black;");
                            statusLabel.setStyle("-fx-text-fill: black;");
                        }

                        setGraphic(borderPane);
                    }
                }

                // Helper method to create a label with fixed width
                private Label createLabel(String text, double width) {
                    Label label = new Label(text);
                    label.setPrefWidth(width);
                    label.setMaxWidth(Region.USE_PREF_SIZE);
                    return label;
                }

                // Method to show/hide additional claim details upon clicking the down-arrow button
                private void toggleDetails(Button downArrowButton) {
                    if (detailsHBox.getChildren().isEmpty()) {
                        Claim claim = getItem();
                        if (claim != null) {
                            Label claimDateLabel = new Label("Claim Date: " + claim.getClaimDate().format(DateTimeFormatter.ofPattern("MMM d, yyyy")));
                            Label examDateLabel = new Label("Exam Date: " + claim.getExamDate().format(DateTimeFormatter.ofPattern("MMM d, yyyy")));
                            Label amountLabel = new Label("Claim Amount: $" + claim.getClaimAmount());
                            Label bankingInfoLabel = new Label("Receiver Banking Info: " + claim.getReceiverBankingInfo());

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