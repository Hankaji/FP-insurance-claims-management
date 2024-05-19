package com.hankaji.icm;

import com.hankaji.icm.database.CreateSession;
import com.hankaji.icm.models.Claim;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.User;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.models.customer.PolicyOwner;
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
