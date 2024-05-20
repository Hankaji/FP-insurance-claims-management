package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class ViewPolicyHoldersController {
    private final SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public ViewPolicyHoldersController() {
    }

    public List<Customer> getPossiblePolicyHolders() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Customer C WHERE C.user.role = :policy_holder_role OR (C.user.role = :dependent_role AND C.holder IS NULL)";
            Query<Customer> query = session.createQuery(hql, Customer.class);
            query.setParameter("policy_holder_role", User.Roles.POLICY_HOLDER);
            query.setParameter("dependent_role", User.Roles.DEPENDENT);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
