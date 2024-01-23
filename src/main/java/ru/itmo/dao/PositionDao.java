package ru.itmo.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.itmo.entity.Position;
import ru.itmo.utils.HibernateSessionFactoryUtil;

import javax.persistence.PersistenceException;
import java.util.List;

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

    public List<Position> getAllPositions(){

        List<Position> positions = (List<Position>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Position  ").list();
        return positions;

    }

    public Position tryFindPositionAndCompany(String positionName, String companyName){

        List<Object[]> positionAndCompany =  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Position as position join Company as company on position.company = company.id where position.positionName='" + positionName +  "' and company.companyName = '" + companyName + "'").list();
        if (positionAndCompany.size()==0){
            return null;
        }
        return (Position) positionAndCompany.get(0)[0];

    }

}
