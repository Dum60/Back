package myBatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import shared.Company;

import java.util.List;

@Mapper
public interface CompanyMapper {

    @Select("insert into carmanufacturing.company(Name, CreationDate) values (#{name}, #{creationDate}) returning id")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Company save(Company entity);

    @Delete("delete from carmanufacturing.company where id = #{id}")
    void deleteById(int id);

    @Delete("delete from carmanufacturing.company")
    void deleteAll();

    @Update("update carmanufacturing.company set Name = #{name}, CreationDate = #{creationDate} where id = #{id}")
    void update(Company entity);

    @Select("select * from carmanufacturing.company where id = #{id}")
    Company getById(int id);

    @Select("select * from carmanufacturing.company")
    List<Company> getAll();
}
