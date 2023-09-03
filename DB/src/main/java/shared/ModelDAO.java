package shared;

import java.util.List;

public interface ModelDAO {
    Model Save(Model entity);

    void DeleteById(int id);

    void DeleteAll();

    Model Update(Model entity);

    Model GetById(int id);

    List<Model> GetAll();

    List<Model> GetAllByCompanyId(int id);
}
