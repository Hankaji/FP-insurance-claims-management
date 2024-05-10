package com.hankaji.icm.views;

import com.hankaji.icm.claim.Claim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClaimController {

    private TableView<Claim> claimTableView;

    public AnchorPane createUI() {
        // Create TableView
        claimTableView = new TableView<>();

        // Define columns
        TableColumn<Claim, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));

        TableColumn<Claim, LocalDateTime> claimDateColumn = new TableColumn<>("Claim Date");
        claimDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimDate()));

        TableColumn<Claim, String> insuredPersonColumn = new TableColumn<>("Insured Person");
        insuredPersonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuredPerson()));

        TableColumn<Claim, String> cardNumberColumn = new TableColumn<>("Card Number");
        cardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));

        TableColumn<Claim, LocalDateTime> examDateColumn = new TableColumn<>("Exam Date");
        examDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExamDate()));

        TableColumn<Claim, Integer> claimAmountColumn = new TableColumn<>("Claim Amount");
        claimAmountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getClaimAmount()).asObject());

        TableColumn<Claim, Claim.Status> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStatus()));

        TableColumn<Claim, String> receiverBankingInfoColumn = new TableColumn<>("Receiver Banking Info");
        receiverBankingInfoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReceiverBankingInfo()));

        // Add columns to TableView
        claimTableView.getColumns().addAll(idColumn, claimDateColumn, insuredPersonColumn, cardNumberColumn,
                examDateColumn, claimAmountColumn, statusColumn, receiverBankingInfoColumn);

        // Add buttons
        Button viewAllButton = new Button("View All Claims");
        Button searchButton = new Button("Search");
        Button deleteButton = new Button("Delete");

        // Add button actions
        viewAllButton.setOnAction(event -> loadClaimsData());
        searchButton.setOnAction(event -> searchClaim());
        deleteButton.setOnAction(event -> deleteClaim());

        // Create UI layout
        AnchorPane root = new AnchorPane();
        root.getChildren().addAll(claimTableView, viewAllButton, searchButton, deleteButton);

        // Set layout constraints
        AnchorPane.setTopAnchor(claimTableView, 10.0);
        AnchorPane.setLeftAnchor(claimTableView, 10.0);
        AnchorPane.setRightAnchor(claimTableView, 10.0);
        AnchorPane.setBottomAnchor(claimTableView, 70.0);

        AnchorPane.setLeftAnchor(viewAllButton, 10.0);
        AnchorPane.setBottomAnchor(viewAllButton, 10.0);

        AnchorPane.setLeftAnchor(searchButton, 110.0);
        AnchorPane.setBottomAnchor(searchButton, 10.0);

        AnchorPane.setLeftAnchor(deleteButton, 210.0);
        AnchorPane.setBottomAnchor(deleteButton, 10.0);

        return root;
    }

    private void searchClaim() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Claim");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter Claim ID:");

        dialog.showAndWait().ifPresent(id -> {
            ObservableList<Claim> items = claimTableView.getItems();
            items.clear();

            // Read JSON file and find the claim with the input ID
            try {
                String content = Files.readString(Paths.get("data/default/Claim.json"));
                JSONArray jsonArray = new JSONArray(content);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss", Locale.ENGLISH);
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

                        // Create and add the found claim to the TableView
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
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                // Handle exceptions
            }
        });
    }

    private void deleteClaim() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Claim");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter Claim ID:");

        dialog.showAndWait().ifPresent(id -> {
            ObservableList<Claim> items = claimTableView.getItems();
            items.forEach(claim -> {
                if (claim.getId().equals(id)) {
                    items.remove(claim);
                }
            });
        });
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
}