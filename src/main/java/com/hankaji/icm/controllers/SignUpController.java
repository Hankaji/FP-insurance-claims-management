package com.hankaji.icm.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.errors.UserExistsException;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.models.customer.PolicyOwner;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SignUpController {

    private SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public SignUpController() {}

    public String[] getAllOwnder() {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            String hql = "SELECT PO.name FROM PolicyOwner PO";
            Query<String> query = session.createQuery(hql, String.class);
            List<String> owners = query.list();

            tx.commit();

            return owners.toArray(new String[owners.size()]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new String[0];
    }

    public void SignUp(String fullName, String email, String password, String owner, User.Roles accountType) throws UserExistsException {
        try {
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

            String hql = "SELECT U.email FROM User U WHERE U.email = :email";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("email", email);
            String emails = query.uniqueResult();

            if (emails != null) {
                System.out.println("Email already exists");
                throw new UserExistsException();
            }

            // Find the owner
            String ownerHql = "FROM PolicyOwner PO WHERE PO.name = :name";
            Query<PolicyOwner> ownerQuery = session.createQuery(ownerHql, PolicyOwner.class);
            ownerQuery.setParameter("name", owner);
            PolicyOwner policyOwner = ownerQuery.uniqueResult();

            if (policyOwner == null) {
                System.out.println("Owner not found");
                return;
            }

            // Create a new user
            String encryptedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            User user = new User(fullName, email, encryptedPassword, accountType);

            // Create a new Customer
            switch (accountType) {
                case User.Roles.DEPENDENT:
                    Customer customer = new Customer(user.getId().toString());
                    InsuranceCard insuranceCard = new InsuranceCard(policyOwner);

                    customer.setInsuranceCard(insuranceCard);

                    session.persist(user);
                    session.persist(insuranceCard);
                    session.persist(customer);
                    break;
                default:
                    break;
            }

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }
}
