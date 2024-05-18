package com.hankaji.icm;

import com.hankaji.icm.database.CreateSession;
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
            Customer son = new Customer(657294937L, UUID.fromString("654e3214-308d-41bb-a307-190a62068bc9"));
            Customer aMinh = new Customer(7641741157L, UUID.fromString("d62bbbfd-ba82-43f9-af58-2be25c1c1f5b"));
            Customer phuc = new Customer(123456543L, UUID.fromString("c3ccca87-ea99-4cda-a535-8ab8e5edb518"));

            son.setHolder(phuc);
            aMinh.setHolder(phuc);
            // do sth
            session.persist(son);
            session.persist(aMinh);

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
