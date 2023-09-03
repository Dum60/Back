package hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import shared.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements shared.CompanyDAO {
    @Override
    public Company Save(Company entity) {
        hibernate.Company tmp = new hibernate.Company(entity.getName(), entity.getCreationDate());
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            entity.setId((Integer) session.save(tmp));
            transaction.commit();
        }
        return entity;
    }

    @Override
    public void DeleteById(int id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery(String.format("delete from Company where id = %d", id)).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void DeleteAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from company").executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public Company Update(Company entity) {
        hibernate.Company tmp = new hibernate.Company(entity.getName(), entity.getCreationDate());
        tmp.setId(entity.getId());
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(tmp);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public Company GetById(int id) {
        hibernate.Company tmp;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            tmp = session.get(hibernate.Company.class, id);
        }
        return new Company(tmp.getId(), tmp.getName(), tmp.getCreationdate());
    }

    @Override
    public List<Company> GetAll() {
        List<Company> companies = new ArrayList<>();
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            List<hibernate.Company> tmps = session.createQuery("From company").list();
            for (hibernate.Company tmp : tmps) {
                Company company = new Company(tmp.getId(), tmp.getName(), tmp.getCreationdate());
                companies.add(company);
            }
        }
        return companies;
    }
}
