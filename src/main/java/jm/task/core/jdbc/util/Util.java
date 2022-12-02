package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "SAYAN007my";

    /*======================Configuration for Hibernate===============================*/

    private static StandardServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;


    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, JDBC_DRIVER);
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USERNAME);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "create-drop");
        return properties;
    }

    public static void buildSessionFactory() {

        try {
            serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(getProperties()).build();

            MetadataSources sources = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(User.class);

            sessionFactory = sources.buildMetadata().buildSessionFactory();
            System.out.println("SessionFactory built successfully");
        } catch (Exception e) {
            System.out.println("SessionFactory's creation failed");
            shutdown();
        }

    }

    public static void shutdown() {
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    public static Session getSession() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

    public static void closeSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException ignored) {
            }
        }
    }

    public static void rollbackQuietly(Transaction tx) {
        if (tx != null) {
            try {
                tx.rollback();
                System.out.println("Transaction rolled back successfully");
            } catch (HibernateException he) {
                System.out.println("HibernateException in rollback" + he.getMessage());
            }
        }


    }
}