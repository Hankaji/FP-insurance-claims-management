package com.hankaji.icm;

import com.hankaji.icm.database.CreateSession;
import com.hankaji.icm.models.InsuranceCard;
import com.hankaji.icm.models.customer.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.UUID;

public class fakeMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = CreateSession.innit();


        // OPEN SESSION
        try {
            Session session = sessionFactory.openSession();
            // start transaction
            Transaction tx = session.beginTransaction();

            // do sth
            InsuranceCard customer = new InsuranceCard(123456543L);
            session.persist(customer);

            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
