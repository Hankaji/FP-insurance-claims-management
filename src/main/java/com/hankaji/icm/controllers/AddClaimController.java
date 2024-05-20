package com.hankaji.icm.controllers;

import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.views.components.ImageUploadForm;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddClaimController {
    
    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public AddClaimController() {
        // Instantiate the CustomerController
    }

    public void addClaim(TextField claimTitleTF, TextArea claimDescriptionTF, TextField claimAmount,
            TextField receivedBankingInfo, ImageUploadForm imageUploadForm) {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            // Get the current user
            User user = session.get(User.class, UserSession.getInstance().getUserId());

            // From current user get Customer
            String hql = "FROM Customer C WHERE C.user.id = :user_id";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("user_id", user.getId());
            Customer customer = query.uniqueResult();

            // Get cardnumber
            Long cardNumber = customer.getInsuranceCardNumber();

            // Get insured person id
            String insuredPersonId = customer.getId();

            // Get the current date
            LocalDateTime claimDate = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String claimFormattedDate = claimDate.format(dateFormatter);

            // Set the status to NEW
            Claim.Status status = Claim.Status.NEW;

            // Print the claim information
            // System.out.println("Claim ID: " + claimId);
            System.out.println("User ID: " + insuredPersonId);
            System.out.println("Exam Date: " + "N/A");
            System.out.println("Claim Amount: " + claimAmount.getText());
            System.out.println("Status: " + status);
            System.out.println("Received Banking Info: " + receivedBankingInfo.getText());
            System.out.println("Claim Date: " + claimFormattedDate);
            System.out.println("Document: " + "N/A");
            System.out.println("Card Number: " + cardNumber);
            // System.out.println("Claim Title: " + claimTitleTF.getText());
            // System.out.println("Claim Description: " + claimDescriptionTF.getText());

            // Print the names of the selected image files
            List<File> selectedFiles = imageUploadForm.getSelectedFiles();
            if (!selectedFiles.isEmpty()) {
                System.out.println("Uploaded Images:");
                for (File file : selectedFiles) {
                    System.out.println(file.getName());
                }
            } else {
                System.out.println("No images were uploaded.");
            }

            // Create a new claim
            Claim claim = new Claim(
                    claimTitleTF.getText(),
                    claimDescriptionTF.getText(),
                    customer,
                    claimDate,
                    null,
                    null,
                    Double.valueOf(claimAmount.getText()),
                    status,
                    receivedBankingInfo.getText());
            
            session.persist(claim);

            tx.commit();

            // Clear all the fields
            claimTitleTF.clear();
            claimDescriptionTF.clear();
            claimAmount.clear();
            receivedBankingInfo.clear();
            imageUploadForm.clearSelectedFiles();

            // Show a notification
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Claim Submission");
            alert.setHeaderText(null);
            alert.setContentText("Claim has been submitted successfully!");

            alert.showAndWait();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}
