package com.hankaji.icm.database;


import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/*
Remember to set this up ONLY ONCE at the start of the application
And remember to TEAR IT DOWN at the end
 */
public class CreateSession {
    private static SessionFactory sessionFactory;

    public static void connect() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder().configure()
                        .build();
        try {
            sessionFactory =
                    new MetadataSources(registry)
                            .buildMetadata()
                            .buildSessionFactory();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we
            // had trouble building the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void tearDown(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }
}
