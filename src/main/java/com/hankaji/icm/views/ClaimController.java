package com.hankaji.icm.views;

import com.hankaji.icm.claim.Claim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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

    private TableView<Claim> claimTableView;

    public AnchorPane createUI() {
        // Create VBox to hold all components
        VBox root = new VBox();
        root.setSpacing(10);
        root.setStyle("-fx-background-color: #2B4A78;"); // Set background color to dark blue

        // Add title
        Text title = new Text("INSURANCE CLAIM PAGE");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        title.setFill(Color.WHITE);
        VBox.setMargin(title, new Insets(10, 0, 10, 0)); // Add margin to title

        // Create HBox to hold title
        HBox titleBox = new HBox();
        titleBox.getChildren().add(title);
        titleBox.setAlignment(Pos.CENTER); // Center align title
        root.getChildren().add(titleBox);

        // Create HBox to hold search button and text field
        HBox searchBox = new HBox();
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.TOP_RIGHT); // Right align search button and text field

        TextField searchField = new TextField();
        searchField.setPromptText("Enter Claim ID");
        Button searchButton = new Button("Search");

        searchButton.setOnAction(event -> searchClaim(searchField.getText()));

        searchBox.getChildren().addAll(searchField, searchButton);
        searchBox.setPadding(new Insets(10));
        searchBox.setStyle("-fx-background-color: #2B4A78;");

        // Add search box to the root VBox
        root.getChildren().add(searchBox);

        // Create HBox to hold buttons
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER); // Center align buttons

        // Add buttons
        Button viewAllButton = new Button("View All Claims");
        Button deleteButton = new Button("Delete A Claim");
        buttonBox.getChildren().addAll(viewAllButton, deleteButton);

        // Add button actions
        viewAllButton.setOnAction(event -> loadClaimsData());
        deleteButton.setOnAction(event -> deleteClaim());

        // Add buttonBox to VBox
        root.getChildren().add(buttonBox);

        // Add TableView
        claimTableView = new TableView<>();
        TableColumn<Claim, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        idColumn.setPrefWidth(100); // Set preferred width for ID column
        TableColumn<Claim, LocalDateTime> claimDateColumn = new TableColumn<>("Claim Date");
        claimDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimDate()));
        claimDateColumn.setPrefWidth(140); // Set preferred width for Claim Date column
        TableColumn<Claim, String> insuredPersonColumn = new TableColumn<>("Insured Person");
        insuredPersonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuredPerson()));
        insuredPersonColumn.setPrefWidth(130); // Set preferred width for Insured Person column
        TableColumn<Claim, String> cardNumberColumn = new TableColumn<>("Card Number");
        cardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
        cardNumberColumn.setPrefWidth(90); // Set preferred width for Card Number column
        TableColumn<Claim, LocalDateTime> examDateColumn = new TableColumn<>("Exam Date");
        examDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExamDate()));
        examDateColumn.setPrefWidth(140); // Set preferred width for Exam Date column
        TableColumn<Claim, Integer> claimAmountColumn = new TableColumn<>("Amount");
        claimAmountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getClaimAmount()).asObject());
        claimAmountColumn.setPrefWidth(60); // Set preferred width for Claim Amount column
        TableColumn<Claim, Claim.Status> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStatus()));
        statusColumn.setPrefWidth(100); // Set preferred width for Status column
        TableColumn<Claim, String> receiverBankingInfoColumn = new TableColumn<>("Receiver Banking Info");
        receiverBankingInfoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReceiverBankingInfo()));
        receiverBankingInfoColumn.setPrefWidth(200); // Set preferred width for Receiver Banking Info column
        claimTableView.getColumns().addAll(idColumn, claimDateColumn, insuredPersonColumn, cardNumberColumn, examDateColumn, claimAmountColumn, statusColumn, receiverBankingInfoColumn);
        root.getChildren().add(claimTableView);

        // Create AnchorPane as container
        AnchorPane container = new AnchorPane();
        container.getChildren().add(root);
        container.setStyle("-fx-background-color: #2B4A78;"); // Set background color to dark blue

        // Set layout constraints
        VBox.setVgrow(claimTableView, Priority.ALWAYS); // Allow TableView to grow vertically
        AnchorPane.setTopAnchor(root, 10.0); // Set top anchor to create space for title
        AnchorPane.setLeftAnchor(root, 10.0);
        AnchorPane.setRightAnchor(root, 10.0);
        AnchorPane.setBottomAnchor(root, 40.0);

        return container;
    }

    private void loadClaimsData() {
        try {
            // Read JSON file
            String content = Files.readString(Paths.get("data/default/Claim.json"));

            // Parse JSON array
            JSONArray jsonArray = new JSONArray(content);

            // Convert JSON objects to Claim objects
            List<Claim> claims = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss", Locale.ENGLISH);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                LocalDateTime claimDate = LocalDateTime.parse(jsonObject.getString("claimDate"), formatter);
                LocalDateTime examDate = LocalDateTime.parse(jsonObject.getString("examDate"), formatter);

                // Parse documents array
                JSONArray documentsArray = jsonObject.getJSONArray("documents");
                List<String> documents = new ArrayList<>();
                for (int j = 0; j < documentsArray.length(); j++) {
                    documents.add(documentsArray.getString(j));
                }

                // Create Claim object
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

            // Clear and Add claims to TableView
            claimTableView.getItems().clear();
            claimTableView.getItems().addAll(claims);

        } catch (IOException | JSONException e) {
            // Handle exceptions
            e.printStackTrace();
            // You might want to display an error message to the user here
        }
    }

    private void searchClaim(String id) {
        // Read JSON file and find the claim with the input ID
        try {
            String content = Files.readString(Paths.get("data/default/Claim.json"));
            JSONArray jsonArray = new JSONArray(content);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss", Locale.ENGLISH);
            ObservableList<Claim> items = FXCollections.observableArrayList(); // Create new ObservableList
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("id").equals(id)) {
                    LocalDateTime claimDate = LocalDateTime.parse(jsonObject.getString("claimDate"), formatter);
                    LocalDateTime examDate = LocalDateTime.parse(jsonObject.getString("examDate"), formatter);

                    // Parse documents array
                    JSONArray documentsArray = jsonObject.getJSONArray("documents");
                    List<String> documents = new ArrayList<>();
                    for (int j = 0; j < documentsArray.length(); j++) {
                        documents.add(documentsArray.getString(j));
                    }

                    // Create and add the found claim to the ObservableList
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
                    break; // Stop searching after finding the claim
                }
            }

            // Clear TableView and set the items
            claimTableView.getItems().clear();
            claimTableView.setItems(items);

            if (items.isEmpty()) {
                // Show message if claim with given ID is not found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No claim found with ID: " + id);
                alert.showAndWait();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private void deleteClaim() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Claim");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter Claim ID:");

        dialog.showAndWait().ifPresent(id -> {
            ObservableList<Claim> items = claimTableView.getItems();
            Claim claimToRemove = null;
            for (Claim claim : items) {
                if (claim.getId().equals(id)) {
                    claimToRemove = claim;
                    break;
                }
            }

            if (claimToRemove != null) {
                // Remove claim from TableView
                items.remove(claimToRemove);

                // Remove claim from JSON file
                try {
                    // Read JSON file
                    Path filePath = Paths.get("data/default/Claim.json");
                    List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

                    // Parse JSON array
                    JSONArray jsonArray = new JSONArray(String.join("\n", lines));

                    // Create updated JSON array without the deleted claim
                    JSONArray updatedArray = new JSONArray();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (!jsonObject.getString("id").equals(id)) {
                            updatedArray.put(jsonObject);
                        }
                    }

                    // Write updated JSON array back to file with original formatting
                    Files.write(filePath, Collections.singletonList(updatedArray.toString(2)), StandardCharsets.UTF_8);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    // Handle exceptions
                }
            } else {
                // Show error message if claim is not found
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Claim with ID " + id + " not found.");
                alert.showAndWait();
            }
        });
    }
}