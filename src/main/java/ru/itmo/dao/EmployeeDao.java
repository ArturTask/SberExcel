package ru.itmo.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import ru.itmo.entity.Employee;
import ru.itmo.utils.HibernateSessionFactoryUtil;

import javax.persistence.PersistenceException;

public class EmployeeDao {

    public void save(Employee employee) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(employee);
            tx1.commit();
        }catch (PersistenceException e){
            System.out.println("Not unique id"); //todo
        }
    }
}
