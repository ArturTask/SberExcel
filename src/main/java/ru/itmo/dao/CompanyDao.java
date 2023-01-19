package ru.itmo.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.entity.Company;
import ru.itmo.entity.Position;
import ru.itmo.utils.HibernateSessionFactoryUtil;

import javax.persistence.PersistenceException;

public class CompanyDao {

    public void save(Company company) {
        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(company);
            tx1.commit();
        }catch (PersistenceException e){
            e.printStackTrace();
        }
    }

    public Company getCompanyById(int id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Company company = session.get(Company.class,id);
        tx.commit();
        session.close();
        return company;
    }
}
