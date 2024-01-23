package ru.itmo.utils;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.itmo.entity.Company;
import ru.itmo.entity.Employee;
import ru.itmo.entity.Position;


public class HibernateSessionFactoryUtil {
        private static SessionFactory sessionFactory;

        private HibernateSessionFactoryUtil() {}

        public static SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                try {
                    Configuration configuration = new Configuration().configure();
                    configuration.addAnnotatedClass(Employee.class);
                    configuration.addAnnotatedClass(Company.class);
                    configuration.addAnnotatedClass(Position.class);
                    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                    sessionFactory = configuration.buildSessionFactory(builder.build());



                } catch (Exception e) {
                    System.out.println("Exception! from HibernateSessionFactory: " + e.getMessage());
                }
            }
            return sessionFactory;
        }
    }
