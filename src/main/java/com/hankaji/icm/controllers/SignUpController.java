package com.hankaji.icm.controllers;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.database.SessionManager;

public class SignUpController {

    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public SignUpController() {}

    public String[] getAllOwnder() {

        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            String hql = "SELECT U.email FROM User U WHERE U.role = 'owner'";
            Query<String> query = session.createQuery(hql, String.class);
            List<String> owners = query.list();

            tx.commit();

            return owners.toArray(new String[owners.size()]);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }
}
