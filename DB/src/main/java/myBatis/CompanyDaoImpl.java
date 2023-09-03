package myBatis;

import shared.Company;
import shared.DBExceptions;

import java.util.List;

public class CompanyDaoImpl implements shared.CompanyDAO {
    public Company Save(Company entity) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CompanyMapper mapper = session.getMapper(CompanyMapper.class);
            Company saved = mapper.save(entity);
            entity.setId(saved.getId());
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return entity;
    }

    @Override
    public void DeleteById(int id) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CompanyMapper mapper = session.getMapper(CompanyMapper.class);
            mapper.deleteById(id);
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public void DeleteAll() {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CompanyMapper mapper = session.getMapper(CompanyMapper.class);
            mapper.deleteAll();
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public Company Update(Company entity) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CompanyMapper mapper = session.getMapper(CompanyMapper.class);
            mapper.update(entity);
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return entity;
    }

    @Override
    public Company GetById(int id) {
        Company company;
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CompanyMapper mapper = session.getMapper(CompanyMapper.class);
            company = mapper.getById(id);
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return company;
    }

    @Override
    public List<Company> GetAll() {
        List<Company> companies;
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            CompanyMapper mapper = session.getMapper(CompanyMapper.class);
            companies = mapper.getAll();
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return companies;
    }
}
