package com.hankaji.icm.controllers;

import com.hankaji.icm.components.Throbber;
import com.hankaji.icm.database.SessionManager;
import com.hankaji.icm.errors.UserInstanceNotExistedException;
import com.hankaji.icm.lib.UserSession;
import static com.hankaji.icm.lib.Utils.disable;
import static com.hankaji.icm.lib.Utils.enable;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.services.AuthorizationService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Callback;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class ViewDependentController implements Initializable {
    private final SessionFactory sessionFactory = SessionManager.getInstance().getSessionFactory();

    @FXML
    private StackPane rootPane;

    @FXML
    private Label currentRoleLabel;

    @FXML
    private Button becomeHolderBtn;

    @FXML
    private Tooltip becomeHolderToolTip;

    @FXML
    private HBox chooseHolderBox;

    @FXML
    private HBox holderNameBox;

    @FXML
    private Label holderNameLabel;

    @FXML
    private Label noHolderLabel;

    @FXML
    private Button chooseHolderBtn;

    @FXML
    private Button switchHolderBtn;

    @FXML
    private ListView<Customer> depListView;

    @FXML
    private StackPane noDepListView;

    private Throbber spinner = new Throbber();

    private VBox chooseHolderContainer;

    private ListView<Customer> chooseHolderList;

    public ViewDependentController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        holderNameBox.setManaged(false);
        holderNameBox.setVisible(false);

        noHolderLabel.setManaged(true);
        noHolderLabel.setVisible(true);

        chooseHolderBtn.setManaged(true);
        chooseHolderBtn.setVisible(true);

        switchHolderBtn.setManaged(false);
        switchHolderBtn.setVisible(false);

        disable(depListView);
        depListView.setCellFactory(new ListCellFactory());

        chooseHolderContainer = new VBox(8);
        chooseHolderContainer.setManaged(false);
        chooseHolderContainer.setVisible(false);
        chooseHolderContainer.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.7));
        chooseHolderContainer.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.7));
        chooseHolderContainer.setStyle(
                "-fx-background-color: #f0f0f0; -fx-border-color: #c1c1c1; -fx-border-radius: 8px; -fx-padding: 16px");
        StackPane.setAlignment(chooseHolderContainer, Pos.CENTER);

        chooseHolderList = new ListView<>();
        chooseHolderList.setManaged(false);

        Button closeBtn = new Button("Close");
        closeBtn.getStyleClass().add("fx-button");
        closeBtn.setOnAction(e -> {
            disable(chooseHolderContainer);
        });

        chooseHolderContainer.getChildren().addAll(chooseHolderList, spinner, closeBtn);

        rootPane.getChildren().add(chooseHolderContainer);

        switchHolderBtn.setOnAction(e -> {
            openPHList();
        });

        chooseHolderBtn.setOnAction(e -> {
            openPHList();
        });

        chooseHolderList.setOnMouseClicked(e -> {
            Customer selected = chooseHolderList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                assignPolicyHolderToDependent(selected.getId());
                disable(chooseHolderContainer);
                getPolicyHolder();
            }
        });

        try {
            if (AuthorizationService.hasRoles(User.Roles.POLICY_HOLDER)) {
                currentRoleLabel.setText("You are currently a policy holder");

                // Disable the "Become Policy Holder" button if the user is already a policy
                // holder
                becomeHolderBtn.setDisable(true);
                becomeHolderToolTip.setText("You are already a policy holder");

                enable(depListView);
                disable(noDepListView);
                disable(chooseHolderBox);

                // ((VBox) (rootPane.getChildren().get(0))).getChildren().add(2, spinner);

                getDependents().thenAccept(depList -> {
                    depListView.getItems().addAll(depList);
                });

            } else {
                // Get the policy holder's name
                getPolicyHolder();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPHList() {
        try {
            chooseHolderContainer.setManaged(true);
            chooseHolderContainer.setVisible(true);
            chooseHolderList.getItems().clear();
            chooseHolderList.setCellFactory(new ListCellFactory());

            enable(spinner);
            getPossiblePolicyHolders().thenAccept(phList -> {
                chooseHolderList.getItems().addAll(phList);
            }).thenAccept(v -> {
                disable(spinner);
                chooseHolderList.setManaged(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized void getPolicyHolder() {
        CompletableFuture.supplyAsync(() -> {
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

                if (customer.getHolder() != null) {
                    noHolderLabel.setManaged(false);
                    noHolderLabel.setVisible(false);

                    holderNameBox.setManaged(true);
                    holderNameBox.setVisible(true);

                    chooseHolderBtn.setManaged(false);
                    chooseHolderBtn.setVisible(false);

                    switchHolderBtn.setManaged(true);
                    switchHolderBtn.setVisible(true);

                    becomeHolderBtn.setDisable(true);
                    becomeHolderToolTip.setText("You already have a policy holder");
                }

                tx.commit();

                return customer.getHolder().getUser().getFullname();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }).thenAccept(holderName -> {
            holderNameLabel.setText(holderName);
        });
    }

    public CompletableFuture<List<Customer>> getPossiblePolicyHolders() {
        return CompletableFuture.supplyAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                String hql = "FROM Customer C WHERE C.user.role = :policy_holder_role";
                Query<Customer> query = session.createQuery(hql, Customer.class);
                query.setParameter("policy_holder_role", User.Roles.POLICY_HOLDER);
                return query.list();
            } catch (Exception e) {
                e.printStackTrace();
                return List.of();
            }
        });
    }

    public CompletableFuture<List<Customer>> getDependents() {
        return CompletableFuture.supplyAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                // Get the current user
                User user = UserSession.getInstance().getUser();

                // Check if the user is a Customer with role POLICY_HOLDER
                if (user != null && AuthorizationService.hasRoles(User.Roles.POLICY_HOLDER)) {
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
        });
    }

    public boolean assignPolicyHolderToDependent(String policyHolderId) {
        try (Session session = sessionFactory.openSession()) {
            User user = UserSession.getInstance().getUser();

            if (user != null && user.getRole() == User.Roles.DEPENDENT) {
                String hql = "FROM Customer C WHERE C.user.id = :user_id";
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

    public class ListCellFactory implements Callback<ListView<Customer>, ListCell<Customer>> {
        @Override
        public ListCell<Customer> call(ListView<Customer> param) {
            return new ListCell<>() {
                @Override
                protected void updateItem(Customer cus, boolean empty) {
                    super.updateItem(cus, empty);

                    if (empty || cus == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox container = new HBox(8);

                        VBox cId = createLabel("ID", cus.getId());
                        VBox cName = createLabel("Full Name", cus.getUser().getFullname());

                        container.getChildren().addAll(cId, cName);

                        setGraphic(container);
                    }
                }
            };
        }

        // Helper method to create a label with fixed width
        private VBox createLabel(String category, String text) {
            VBox container = new VBox(4);
            container.setAlignment(Pos.CENTER_LEFT);
            HBox.setHgrow(container, Priority.ALWAYS);

            Label cateLabel = new Label(category);
            cateLabel.setFont(Font.font(10));
            cateLabel.setTextFill(Color.web("#0f0f0f"));

            Label value = new Label(text);

            container.getChildren().addAll(cateLabel, value);
            return container;
        }
    }

}
