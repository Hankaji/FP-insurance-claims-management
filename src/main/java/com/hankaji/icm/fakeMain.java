package com.hankaji.icm;

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
import java.util.UUID;

public class fakeMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = CreateSession.innit();

        // OPEN SESSION
        try {
            Session session = sessionFactory.openSession();
            // start transaction
            Transaction tx = session.beginTransaction();

            UserSession.createSession(UUID.fromString("d180b9fb-eeac-4e38-8159-d14d87993462"));

            UpdateClaimStatusController con = new UpdateClaimStatusController();

            Claim claim = session.find(Claim.class, "f-5831110760");

            con.updateClaimStatus(claim, Claim.Status.PROCESSING);
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
