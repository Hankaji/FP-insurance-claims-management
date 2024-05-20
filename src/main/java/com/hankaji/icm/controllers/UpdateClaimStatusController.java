package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.providers.Provider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UpdateClaimStatusController {
    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public UpdateClaimStatusController() {
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
                // Check if the managerId is NULL for certain status updates
                if ((newStatus == Claim.Status.DONE || newStatus == Claim.Status.DENIED) && provider.getManager() != null) {
                    throw new IllegalAccessException("Only Insurance Managers can set status to DONE or DENIED.");
                }

                // Update the claim status
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

