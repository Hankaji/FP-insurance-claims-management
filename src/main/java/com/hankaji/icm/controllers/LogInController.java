package com.hankaji.icm.controllers;

import java.util.Objects;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.User;
import com.hankaji.icm.services.UserPreferences;
import com.hankaji.icm.views.SignUpPage;

import at.favre.lib.crypto.bcrypt.BCrypt;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LogInController {

    private UserPreferences userPreferences;

    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public LogInController() {}

    public void checkLoginStatus(Scene oldScene) {
        userPreferences = new UserPreferences();
        UUID userId = userPreferences.getUserId();
        if (userId != null) {
            try {
                Session session = sessionFactory.openSession();
                Transaction tx = session.beginTransaction();

                User user = session.get(User.class, userId);
                if (user != null) {
                    UserSession.createSession(user);
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RootView.fxml")));
                    Stage stage = (Stage) oldScene.getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                }

                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleLoginButton(ActionEvent event, String email, String password) {
        System.out.println("Login button clicked");

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
                    // System.out.println("Password verified");
                    // Set scene
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RootView.fxml")));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);

                    // Store the user id in the session
                    UserSession.createSession(user);
                    stage.setScene(scene);

                    // Store the user id in the preferences
                    userPreferences.saveUserId(user.getId().toString());
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

    public void switchToSignup(MouseEvent event) {
        try {
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(new SignUpPage());
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
