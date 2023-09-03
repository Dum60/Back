package hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ModelDaoImpl implements shared.ModelDAO {
    @Override
    public shared.Model Save(shared.Model entity) {
        hibernate.Model tmp = new hibernate.Model(entity.getId(),
                entity.getName(),
                entity.getLength(),
                entity.getWidth(),
                entity.getBody(),
                new Company(entity.getCompanyId()));
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            session.save(tmp);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public void DeleteById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.createQuery(String.format("delete from Model where id = %d", id)).executeUpdate();
        }
    }

    @Override
    public void DeleteAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.createQuery("delete from model").executeUpdate();
        }
    }

    @Override
    public shared.Model Update(shared.Model entity) {
        hibernate.Model tmp = new hibernate.Model(entity.getId(),
                entity.getName(),
                entity.getLength(),
                entity.getWidth(),
                entity.getBody(),
                new Company(entity.getCompanyId()));
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.getTransaction();
            session.update(tmp);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public shared.Model GetById(int id) {
        hibernate.Model tmp;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            tmp = session.get(hibernate.Model.class, id);
        }
        return new shared.Model(tmp.getId(),
                tmp.getName(),
                tmp.getLength(),
                tmp.getWidth(),
                tmp.getBody(),
                tmp.getCompany().getId());
    }

    @Override
    public List<shared.Model> GetAll() {
        List<shared.Model> models = new ArrayList<>();
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<hibernate.Model> tmps = session.createQuery("From model").list();
            for (hibernate.Model tmp : tmps) {
                shared.Model model = new shared.Model(tmp.getId(),
                        tmp.getName(),
                        tmp.getLength(),
                        tmp.getWidth(),
                        tmp.getBody(),
                        tmp.getCompany().getId());
                models.add(model);
            }
        }
        return models;
    }

    @Override
    public List<shared.Model> GetAllByCompanyId(int id) {
        return null;
    }
}
