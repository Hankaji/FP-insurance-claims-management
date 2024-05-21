package com.hankaji.icm.controllers;

import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.errors.UserInstanceNotExistedException;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.services.AuthorizationService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ViewDependentController implements Initializable {
    private final SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    @FXML
    private Label currentRoleLabel;

    @FXML
    private Button becomeHolderBtn;

    @FXML
    private Tooltip becomeHolderToolTip;

    @FXML
    private HBox holderNameBox;

    @FXML
    private Label holderNameLabel;

    @FXML
    private Label noHolderLabel;

    public ViewDependentController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (AuthorizationService.hasRoles(User.Roles.POLICY_HOLDER)) {
                currentRoleLabel.setText("You are currently a policy holder");

                // Disable the "Become Policy Holder" button if the user is already a policy
                // holder
                becomeHolderBtn.setDisable(true);
                becomeHolderToolTip.setText("You are already a policy holder");

                // Get the policy holder's name
                getPolicyHolder().thenAccept(holderName -> {
                    holderNameLabel.setText(holderName);
                }).get();
            } else {
                // Get the policy holder's name
                getPolicyHolder().thenAccept(holderName -> {
                    holderNameLabel.setText(holderName);
                }).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CompletableFuture<String> getPolicyHolder() {
        return CompletableFuture.supplyAsync(() -> {
            UUID userId = null;
            try {
                userId = UserSession.getInstance().getUser().getId();
            } catch (UserInstanceNotExistedException e) {
                throw new RuntimeException("User instance not found");
            }

            return userId;
        }).thenApply(id -> {
            Customer customer;
            try {
                Session session = sessionFactory.openSession();
                Transaction tx = session.beginTransaction();

                String hql = "FROM Customer C WHERE C.user.id = :user_id";
                Query<Customer> query = session.createQuery(hql, Customer.class);
                query.setParameter("user_id", id);
                customer = query.uniqueResult();

                if (customer == null) {
                    throw new RuntimeException("Customer not found");
                }

                if (customer.getHolder() == null) {
                    holderNameBox.setVisible(false);
                    noHolderLabel.setVisible(true);
                }

                tx.commit();

                System.out.println("HOLDER ID: " + customer.getHolder());

                return customer.getHolder();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).thenApply(holderObj -> {
            return holderObj.getUser().getFullname();
        });
    }

    public List<Customer> getDependents() {
        try (Session session = sessionFactory.openSession()) {
            // Get the current user
            User user = UserSession.getInstance().getUser();

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
            User user = UserSession.getInstance().getUser();

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

                        // Update policy holder's role if they were a dependent
                        if (policyHolder.getUser().getRole() == User.Roles.DEPENDENT) {
                            policyHolder.getUser().setRole(User.Roles.POLICY_HOLDER);
                            session.merge(policyHolder);
                        }

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

}
