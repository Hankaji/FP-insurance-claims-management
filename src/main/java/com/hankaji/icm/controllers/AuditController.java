package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.Audit;
import com.hankaji.icm.models.Provider;
import com.hankaji.icm.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AuditController {
    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public AuditController() {
    }

    public void addAudit(String description){
        try (Session session = sessionFactory.openSession()) {
            User user = UserSession.getInstance().getUser();
            Audit audit = new Audit(user, description);
            Transaction tx = session.beginTransaction();
            session.persist(audit);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
