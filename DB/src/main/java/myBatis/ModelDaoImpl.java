package myBatis;

import shared.DBExceptions;
import shared.Model;

import java.util.List;

public class ModelDaoImpl implements shared.ModelDAO {
    @Override
    public Model Save(Model entity) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ModelMapper mapper = session.getMapper(ModelMapper.class);
            Model saved = mapper.save(entity);
            entity.setId(saved.getId());
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return entity;
    }

    @Override
    public void DeleteById(int id) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ModelMapper mapper = session.getMapper(ModelMapper.class);
            mapper.getById(id);
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public void DeleteAll() {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ModelMapper mapper = session.getMapper(ModelMapper.class);
            mapper.deleteAll();
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public Model Update(Model entity) {
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ModelMapper mapper = session.getMapper(ModelMapper.class);
            mapper.update(entity);
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return entity;
    }

    @Override
    public Model GetById(int id) {
        Model model;
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ModelMapper mapper = session.getMapper(ModelMapper.class);
            model = mapper.getById(id);
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return model;
    }

    @Override
    public List<Model> GetAll() {
        List<Model> models;
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ModelMapper mapper = session.getMapper(ModelMapper.class);
            models = mapper.getAll();
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return models;
    }

    @Override
    public List<Model> GetAllByCompanyId(int id) {
        List<Model> models;
        try (var session = MyBatisSessionFactory.getSessionFactory().openSession()) {
            ModelMapper mapper = session.getMapper(ModelMapper.class);
            models = mapper.getByCompanyId(id);
        } catch (Exception e) {
            throw new DBExceptions(e.getMessage());
        }
        return models;
    }
}
