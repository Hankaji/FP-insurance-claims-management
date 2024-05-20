package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.PolicyOwner;
import com.hankaji.icm.models.providers.Provider;
import com.hankaji.icm.views.components.ImageUploadForm;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UpdateClaimController {
    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public UpdateClaimController() {
    }

    public void updateClaimStatus(Claim claim, Claim.Status newStatus) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            // Get the current user
            User user = session.get(User.class, UserSession.getInstance().getUserId());

            // From current user get Provider
            String hql = "FROM Provider P WHERE P.user.id = :user_id";
            Query<Provider> query = session.createQuery(hql, Provider.class);
            query.setParameter("user_id", user.getId());
            Provider provider = query.uniqueResult();

            if (provider != null) {
                // Only allow Providers to change the status
                claim.setStatus(newStatus);
                session.merge(claim);
                tx.commit();
            } else {
                throw new IllegalAccessException("Only Providers can change the claim status.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

