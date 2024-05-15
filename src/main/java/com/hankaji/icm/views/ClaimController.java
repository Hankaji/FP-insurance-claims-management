package com.hankaji.icm.views;

import com.hankaji.icm.claim.Claim;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    private void initialize() {
        // Set the cell factory for the ListView
        claimListView.setCellFactory(new ListViewCellFactory());
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

    private class ListViewCellFactory implements Callback<ListView<Claim>, ListCell<Claim>> {
        @Override
        public ListCell<Claim> call(ListView<Claim> param) {
            return new ListCell<Claim>() {
                @Override
                protected void updateItem(Claim item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        // Create the cell content here
                        Label idLabel = new Label(item.getId());
                        Label insuredPersonLabel = new Label(item.getInsuredPerson());
                        Label cardNumberLabel = new Label(item.getCardNumber());
                        Label statusLabel = new Label(item.getStatus().toString());

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

                        GridPane cellContent = new GridPane();
                        cellContent.addRow(0, createEmptyLabel(), idLabel, insuredPersonLabel, cardNumberLabel, statusLabel, dotsButton);
                        cellContent.getColumnConstraints().addAll(
                                new ColumnConstraints(20), // Spacing column
                                new ColumnConstraints(200),
                                new ColumnConstraints(200),
                                new ColumnConstraints(200),
                                new ColumnConstraints(170),
                                new ColumnConstraints(50)
                        );
                        cellContent.setHgap(10.0);
                        cellContent.setVgap(5.0);
                        setGraphic(cellContent);

                        // Handle click event to show more details
                        setOnMouseClicked(event -> {
                            if (!isEmpty() && event.getClickCount() == 2) {
                                showClaimDetails(item); // Implement this method to show more details
                            }
                        });
                    }
                }

                // Helper method to create an empty label for spacing
                private Label createEmptyLabel() {
                    Label emptyLabel = new Label();
                    emptyLabel.setMinWidth(20); // Set width for spacing
                    return emptyLabel;
                }
            };
        }
    }

    private void showClaimDetails(Claim claim) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Claim Details");
        alert.setHeaderText(null);

        // Increase width of the alert dialog
        alert.getDialogPane().setPrefWidth(450);

        // Set content text with line breaks for better readability
        alert.setContentText("ID: " + claim.getId() +
                "\nInsured Person: " + claim.getInsuredPerson() +
                "\nCard Number: " + claim.getCardNumber() +
                "\nStatus: " + claim.getStatus().toString() +
                "\nClaim Amount: " + claim.getClaimAmount() +
                "\nReceiver Banking Info: " + claim.getReceiverBankingInfo());

        alert.showAndWait();
    }

    public Callback<ListView<Claim>, ListCell<Claim>> getListViewCellFactory() {
        return new ListViewCellFactory();
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