package com.hankaji.icm;

import com.hankaji.icm.database.CreateSession;
import com.hankaji.icm.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class fakeMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = CreateSession.innit();


        // OPEN SESSION
        try {
            Session session = sessionFactory.openSession();
            // start transaction
            Transaction tx = session.beginTransaction();
            User user = new User("Hankaji", "hankaji135@gmail.com","1245678", User.Roles.CUSTOMER);
            session.persist(user);

            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
