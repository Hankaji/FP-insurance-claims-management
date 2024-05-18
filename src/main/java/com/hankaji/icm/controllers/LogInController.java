package com.hankaji.icm.controllers;

import java.util.Objects;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.models.User;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

    private Button loginButton;
    private TextField emailField;
    private PasswordField passwordField;
    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public LogInController(Button loginButton, TextField emailField, PasswordField passwordField) {
        this.loginButton = loginButton;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleLoginButton(event);
            }
        });
    }

    private void handleLoginButton(ActionEvent event) {
        System.out.println("Login button clicked");

        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            String hql = "FROM User U WHERE U.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();

            if (user != null) {
                String encryptedPassword = user.getPassword();
                // System.out.println("Typed data: ");
                // System.out.println("Email: " + email);
                // System.out.println("Password: " + password);
                // System.out.println("Database user: ");
                // System.out.println("User found: " + user.getEmail());
                // System.out.println("Password: " + encryptedPassword);

                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), encryptedPassword);
                if (result.verified) {
                    System.out.println("Password verified");
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RootView.fxml")));
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Invalid credentials");
                    alert.setContentText("The email or password is incorrect");
                    alert.showAndWait();
                }
            } else {
                System.out.println("User not found");
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
