package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.UUID;

public class DependentController {
    private final SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    public DependentController() {
    }

    public List<Customer> getDependents() {
        try (Session session = sessionFactory.openSession()) {
            // Get the current user
            User user = session.get(User.class, UserSession.getInstance().getUserId());

            // Check if the user is a Customer with role POLICY_HOLDER
            if (user != null && user.getRole() == User.Roles.POLICY_HOLDER) {
                // Get the Customer associated with this User
                String hql = "FROM Customer C WHERE C.user.id = :user_id";
                Query<Customer> query = session.createQuery(hql, Customer.class);
                query.setParameter("user_id", user.getId());
                Customer policyHolder = query.uniqueResult();

                // If the user is a policy holder, return their dependents
                if (policyHolder != null) {
                    return policyHolder.getDependents();
                }
            }

            // If user is not a policy holder or no dependents found, return an empty list
            return List.of();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
    public boolean assignPolicyHolderToDependent(String policyHolderId) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, UserSession.getInstance().getUserId());
            if (user != null && user.getRole() == User.Roles.DEPENDENT) {
                String hql = "FROM Customer C WHERE C.user.id = :user_id AND C.holder IS NULL";
                Query<Customer> query = session.createQuery(hql, Customer.class);
                query.setParameter("user_id", user.getId());
                Customer dependent = query.uniqueResult();

                if (dependent != null) {
                    Customer policyHolder = session.get(Customer.class, policyHolderId);
                    if (policyHolder != null) {
                        Transaction tx = session.beginTransaction();

                        // Update dependent's holder
                        dependent.setHolder(policyHolder);

                        session.merge(dependent);
                        tx.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean ChangeToHolder(){
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, UserSession.getInstance().getUserId());
            if (user != null && user.getRole() == User.Roles.DEPENDENT) {
                String hql = "FROM Customer C WHERE C.user.id = :user_id AND C.holder IS NULL";
                Query<Customer> query = session.createQuery(hql, Customer.class);
                query.setParameter("user_id", user.getId());
                Customer dependent = query.uniqueResult();

                if (dependent != null) {
                    // Update policy holder's role if they were a dependent
                    if (dependent.getUser().getRole() == User.Roles.DEPENDENT) {
                        dependent.getUser().setRole(User.Roles.POLICY_HOLDER);
                        session.merge(dependent);
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
