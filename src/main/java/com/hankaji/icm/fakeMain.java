package com.hankaji.icm;

import com.hankaji.icm.database.CreateSession;
import com.hankaji.icm.models.customer.Customer;
import com.hankaji.icm.models.customer.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.management.relation.Role;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class fakeMain {
    public static void main(String[] args) {
        SessionFactory sessionFactory = CreateSession.innit();


        // OPEN SESSION
        try (Session session = sessionFactory.openSession()) {
            // start transaction
            Transaction tx = session.beginTransaction();

            List<User> users = session.createQuery("select  u from User u", User.class).list();
            users.forEach(System.out::println);

            tx.commit();
        }

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
