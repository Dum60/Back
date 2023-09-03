package shared;

import java.util.List;

public interface CompanyDAO {
    Company Save(Company entity);

    void DeleteById(int id);

    void DeleteAll();

    Company Update(Company entity);

    Company GetById(int id);

    List<Company> GetAll();
}
