package com.hankaji.icm.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/*
Remember to set this up ONLY ONCE at the start of the application
And remember to TEAR IT DOWN at the end
 */
public class SessionManager {

    private static SessionManager _instance;

    private SessionFactory sessionFactory;

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
        sessionFactory = null;

        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("/configs/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
        return sessionFactory;
    }

    public void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
}
