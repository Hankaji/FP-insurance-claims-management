package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.customer.PolicyOwner;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddCardController {

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField policyOwnerField;

    @FXML
    private TextField expirationDateField;

    private SessionFactory sessionFactory;

    public AddCardController() {
        sessionFactory = SessionManager.getInstance().getSessionFactory();
    }

    @FXML
    private void handleSave() {
        String cardNumber = cardNumberField.getText();
        String policyOwnerName = policyOwnerField.getText();
        String expirationDateString = expirationDateField.getText();

        if (cardNumber.isEmpty() || policyOwnerName.isEmpty() || expirationDateString.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter all details.");
            return;
        }

        LocalDateTime expirationDate;
        try {
            expirationDate = LocalDateTime.parse(expirationDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Invalid expiration date format.");
            return;
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            PolicyOwner policyOwner = session.createQuery("from PolicyOwner where name = :name", PolicyOwner.class)
                    .setParameter("name", policyOwnerName)
                    .uniqueResult();
            if (policyOwner == null) {
                showAlert(Alert.AlertType.ERROR, "Form Error!", "Policy Owner not found.");
                return;
            }

            InsuranceCard card = new InsuranceCard();
            card.setCardNumber(Long.valueOf(cardNumber));
            card.setPolicyOwner(policyOwner);
            card.setExpirationDate(expirationDate);

            session.save(card);
            transaction.commit();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Insurance card added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Error saving insurance card.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
