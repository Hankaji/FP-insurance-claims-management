package com.hankaji.icm.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.procedure.internal.Util;

import com.hankaji.icm.lib.Utils;
import com.hankaji.icm.models.User;
import com.hankaji.icm.services.AuthorizationService;
import com.hankaji.icm.services.UserPreferences;
import com.hankaji.icm.views.CustomerDashboard;
import com.hankaji.icm.views.LogIn;
import com.hankaji.icm.views.components.CardDetails;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class RootViewController implements Initializable {

    @FXML private Circle avatar;

    @FXML private BorderPane rootPane;

    @FXML private Button cusDashboard;

    @FXML private Button cusClaims;

    @FXML private Button cusCards;

    @FXML private Button cusDependents;

    @FXML private Button cusCustomers;

    @FXML private Button logoutBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image = new Image("https://fiverr-res.cloudinary.com/images/q_auto,f_auto/gigs/114131672/original/5f03e84975a3e52c91166d03b89c6af7e061ca44/send-you-a-random-meme-image-that-will-tickle-your-fancy.jpg");
        avatar.setFill(new ImagePattern(image));

        try {
            rootPane.setCenter(FXMLLoader.load(getClass().getResource("/fxml/ClaimView.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        cusDashboard.setOnAction(e -> {
            changeTab(new CustomerDashboard());
        });

        if (!AuthorizationService.hasRoles(User.Roles.POLICY_HOLDER, User.Roles.DEPENDENT)) {
            Utils.disable(cusDashboard);
        }

        cusCards.setOnAction(e -> {
            try {
                Parent cardView = FXMLLoader.load(getClass().getResource("/fxml/CardView.fxml"));
                changeTab(cardView);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        if (!AuthorizationService.hasRoles(User.Roles.ADMIN)) {
            Utils.disable(cusCards);
        }

        cusClaims.setOnAction(e -> {
            try {
                Parent claimView = FXMLLoader.load(getClass().getResource("/fxml/ClaimView.fxml"));
                changeTab(claimView);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        cusDependents.setOnAction(e -> {
            try {
                Parent dependentView = FXMLLoader.load(getClass().getResource("/fxml/DependentViews.fxml"));
                changeTab(dependentView);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        if (!AuthorizationService.hasRoles(User.Roles.DEPENDENT, User.Roles.POLICY_HOLDER)) {
            Utils.disable(cusDependents);
        }

        cusCustomers.setOnAction(e -> {
            try {
                Parent customerView = FXMLLoader.load(getClass().getResource("/fxml/CustomerView.fxml"));
                changeTab(customerView);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        if (!AuthorizationService.hasRoles(User.Roles.ADMIN, User.Roles.PROVIDER)) {
            Utils.disable(cusCustomers);
        }


        logoutBtn.setOnAction(this::handleLogout);
    }

    public void changeTab(Node node) {
        rootPane.setCenter(node);
    }

    private void handleLogout(ActionEvent event) {
        // Clear the user session
        UserPreferences preferences = new UserPreferences();
        preferences.clearUserId();

        // Redirect to the login page
        try {
            LogIn loginView = new LogIn();
            rootPane.getScene().setRoot(loginView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
