package ru.itmo.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.entity.Employee;
import ru.itmo.entity.Position;
import ru.itmo.utils.HibernateSessionFactoryUtil;

import javax.persistence.PersistenceException;

public class PositionDao {

    public void save(Position position) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(position);
            tx1.commit();
        }catch (PersistenceException e){
            e.printStackTrace();
        }
    }
}
