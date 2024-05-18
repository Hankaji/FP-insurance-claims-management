package com.hankaji.icm.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionManager {

    private static SessionManager _instance;
    private static SessionFactory sessionFactory;

    private SessionManager() {
        sessionFactory = init();
    }

    /**
     * Get the instance of the session.
     * Session is automatically initialized when the instance is first called.
     *
     * @return the instance of the session
     */
    public static synchronized SessionManager getInstance() {
        if (_instance == null) {
            _instance = new SessionManager();
        }
        return _instance;
    }

    private SessionFactory init() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("configs/hibernate.cfg.xml") // Ensure this path is correct
                .build();
        try {
            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = getInstance().init();
        }
        return sessionFactory;
    }

    public void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}