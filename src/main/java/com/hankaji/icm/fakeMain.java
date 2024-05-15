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

            // DO STH
//            session.persist(customer);
            // session.load(role, UUID.fromString("8eb4a332-aa57-4879-9a76-d159bbcab9f1"));
            // //DELETE INSTANCE WITH SPECIFIED ID
            // session.remove(role);
            // session.flush();
            // session.clear();
            // session.persist(user);
    //      List<Object[]> users = session.createQuery("SELECT u, r FROM Role r JOIN User u ON u.role_id = r.id").list();
    //      users.forEach(objects -> {
    //        User user1 = (User) objects[0];
    //        Role role1 = (Role) objects[1];
    //        System.out.println("User: " + user1.toString() + ", Role: " + role1.toString());
    //      });



            tx.commit();
        }

        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
