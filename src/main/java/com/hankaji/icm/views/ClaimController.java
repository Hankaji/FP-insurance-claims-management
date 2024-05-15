package com.hankaji.icm.views;

import com.hankaji.icm.claim.Claim;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    private TableView<Claim> claimTableView;

    @FXML
    private TableColumn<Claim, String> idColumn;

    @FXML
    private TableColumn<Claim, LocalDateTime> claimDateColumn;

    @FXML
    private TableColumn<Claim, String> insuredPersonColumn;

    @FXML
    private TableColumn<Claim, String> cardNumberColumn;

    @FXML
    private TableColumn<Claim, LocalDateTime> examDateColumn;

    @FXML
    private TableColumn<Claim, Integer> claimAmountColumn;

    @FXML
    private TableColumn<Claim, Claim.Status> statusColumn;

    @FXML
    private TableColumn<Claim, String> receiverBankingInfoColumn;

    @FXML
    private TextField searchField;

    @FXML
    private void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        claimDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getClaimDate()));
        insuredPersonColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInsuredPerson()));
        cardNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
        examDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExamDate()));
        claimAmountColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getClaimAmount()).asObject());
        statusColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStatus()));
        receiverBankingInfoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReceiverBankingInfo()));

        // Set preferred widths for columns
        idColumn.setPrefWidth(100);
        claimDateColumn.setPrefWidth(140);
        insuredPersonColumn.setPrefWidth(130);
        cardNumberColumn.setPrefWidth(120); // Adjust as needed
        examDateColumn.setPrefWidth(140);
        claimAmountColumn.setPrefWidth(80); // Adjust as needed
        statusColumn.setPrefWidth(100);
        receiverBankingInfoColumn.setPrefWidth(200);
    }

    @FXML
    private void handleViewAll() {
        loadAllClaimsData();
    }

    @FXML
    private void handleSearch() {
        searchClaim(searchField.getText());
    }

    @FXML
    private void handleDelete() {
        deleteClaim();
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

            claimTableView.getItems().clear();
            claimTableView.getItems().addAll(claims);

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

            claimTableView.getItems().clear();
            claimTableView.setItems(items);

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
                items.remove(claimToRemove);

                try {
                    Path filePath = Paths.get("data/default/Claim.json");
                    String content = Files.readString(filePath);
                    JSONArray jsonArray = new JSONArray(content);

                    JSONArray updatedArray = new JSONArray();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (!jsonObject.getString("id").equals(id)) {
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
                alert.setContentText("Claim with ID " + id + " not found.");
                alert.showAndWait();
            }
        });
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