package com.hankaji.icm.views;

import java.util.concurrent.CompletableFuture;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.views.components.CardDetails;

import javafx.geometry.Insets;
import javafx.scene.layout.TilePane;

public class CustomerDashboard extends TilePane {

    SessionFactory sessionFactory;

    public CustomerDashboard() {
        sessionFactory = SessionManager.getInstance().getSessionFactory();
        getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
        getStyleClass().add("customer-dashboard");

        setPadding(new Insets(24));

        CardDetails cardDetails = addCardDetails().join();
        
        getChildren().add(cardDetails);
    }

    private CompletableFuture<CardDetails> addCardDetails() {
        return CompletableFuture.supplyAsync(() -> {
            
            try {
                User user = UserSession.getInstance().getUser();
                Session session = sessionFactory.openSession();
                Transaction tx = session.beginTransaction();

                String hql = "FROM Customer C WHERE C.user.id = :userId";
                Query<Customer> query = session.createQuery(hql, Customer.class);
                query.setParameter("userId", user.getId());
                Customer customer = query.getSingleResult();

                InsuranceCard card = customer.getInsuranceCard();

                CardDetails cardDetails = new CardDetails(card.getCardNumber().toString(), "Active",
                        card.getExpirationDate().toString(), "$1000");

                tx.commit();

                return cardDetails;

            } catch (Exception e) {
                // TODO: handle exception
            }

            return null;
        });
    }
}
