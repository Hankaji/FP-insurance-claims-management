package com.hankaji.icm;

import com.hankaji.icm.controllers.ViewDependentsController;
import com.hankaji.icm.database.CreateSession;
import com.hankaji.icm.controllers.UpdateClaimStatusController;
import com.hankaji.icm.lib.UserSession;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.customer.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
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

            UserSession.createSession(UUID.fromString("9e873579-38d9-4add-b6bb-d2090dd53758"));

            ViewDependentsController con = new ViewDependentsController();

            List<Customer> list = con.getDependents();

            list.forEach(System.out::println);
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
