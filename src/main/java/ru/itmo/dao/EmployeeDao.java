package ru.itmo.dao;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import ru.itmo.entity.Employee;
import ru.itmo.utils.HibernateSessionFactoryUtil;

import javax.persistence.PersistenceException;

public class EmployeeDao {

    public void save(Employee employee) throws PersistenceException {
        @Cleanup
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(employee);
        tx1.commit();
    }
}
