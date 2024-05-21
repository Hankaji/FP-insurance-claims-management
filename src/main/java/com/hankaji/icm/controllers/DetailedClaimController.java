package com.hankaji.icm.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.models.Claim;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DetailedClaimController {

    private final SessionFactory sessionFactory;

    @FXML
    private TextField searchField;

    @FXML
    private Label idField;

    @FXML
    private Label titleField;

    @FXML
    private Label descriptionField;

    @FXML
    private Label claimDateField;

    @FXML
    private Label examDateField;

    @FXML
    private Label documentsPlaceholder;

    @FXML
    private Label claimAmountField;

    @FXML
    private Label statusField;

    @FXML
    private Label receiverBankingInfoField;

    @FXML
    private Label insuranceCardNumberField;

    public DetailedClaimController() {
        this.sessionFactory = SessionManager.getInstance().getSessionFactory();
    }

    @FXML
    public void displayClaim(String claimId) {
        try (Session session = sessionFactory.openSession()) {
            Claim claim = session.get(Claim.class, claimId);
            if (claim != null) {
                idField.setText(claim.getId());
                titleField.setText(claim.getTitle());
                descriptionField.setText(claim.getDescription());
                claimDateField.setText(claim.getClaimDate().toString());
                examDateField.setText(claim.getExamDate().toString());
                // Make sure the following lines set the correct values:
                claimAmountField.setText(Double.toString(claim.getClaimAmount()));
                statusField.setText(claim.getStatus().toString());
                receiverBankingInfoField.setText(claim.getReceiverBankingInfo());
                insuranceCardNumberField.setText(Long.toString(claim.getInsuranceCardNumber()));
            } else {
                clearFields();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("No claim found with ID: " + claimId);
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        descriptionField.setText("");
        claimDateField.setText("");
        examDateField.setText("");
        documentsPlaceholder.setText("");
        claimAmountField.setText("");
        statusField.setText("");
        receiverBankingInfoField.setText("");
        insuranceCardNumberField.setText("");
    }
}