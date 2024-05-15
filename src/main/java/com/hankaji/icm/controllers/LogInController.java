package com.hankaji.icm.controllers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.models.User;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class LogInController {

    private Button loginButton;
    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public LogInController(Button loginButton) {
        this.loginButton = loginButton;
        this.loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleLoginButton(event);
            }
        });
    }

    private void handleLoginButton(ActionEvent event) {
        System.out.println("Login button clicked");

        String email = "hankaji135@gmail.com";
        String password = "";

        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            String hql = "FROM User U WHERE U.email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            User user = query.uniqueResult();

            if (user != null) {
                password = user.getPassword();
                System.out.println("User found: " + user.getEmail());
                System.out.println("Password: " + password);
            } else {
                System.out.println("User not found");
            }

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
