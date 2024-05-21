package com.hankaji.icm;

import com.hankaji.icm.controllers.DependentController;
import com.hankaji.icm.controllers.ViewPolicyHoldersController;
import com.hankaji.icm.database.CreateSession;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

public class fakeMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = CreateSession.innit();

        // OPEN SESSION
        try {
            Session session = sessionFactory.openSession();
            // start transaction
            Transaction tx = session.beginTransaction();

            UserSession.createSession(UUID.fromString("15637c7e-d77f-4906-9b40-81ed01461f61"));

            ViewPolicyHoldersController con1 = new ViewPolicyHoldersController();

            DependentController con = new DependentController();

            // List possible policy holders for the dependent to choose from
            List<Customer> policyHolders = con1.getPossiblePolicyHolders();
            System.out.println("Possible Policy Holders:");
            for (Customer policyHolder : policyHolders) {
                System.out.println(policyHolder.toString());
            }

            // Assuming the dependent chooses a policy holder with ID "chosenPolicyHolderId"
            String chosenPolicyHolderId = "c-4302809"; // Replace with actual ID

            // Assign the chosen policy holder to the dependent
            boolean success = con.assignPolicyHolderToDependent(chosenPolicyHolderId);
            if (success) {
                System.out.println("Policy holder assigned successfully.");
            } else {
                System.out.println("Failed to assign policy holder.");
            }

            //do sth
            tx.commit();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
