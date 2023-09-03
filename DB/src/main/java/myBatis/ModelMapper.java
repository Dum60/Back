package myBatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import shared.Model;

import java.util.List;

@Mapper
public interface ModelMapper {

    @Select("insert into carmanufacturing.model(Name, Length, Width, Body, CompanyId) values (#{name}, #{length}, #{width}, #{body}, #{companyId}) returning id")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Model save(Model entity);

    @Delete("delete from carmanufacturing.model where id = #{id}")
    void deleteById(int id);

    @Delete("delete from Model")
    void deleteAll();

    @Update("update carmanufacturing.model set Name = #{name}, Length = #{length}, Width = #{width}, Body = #{body}, CompanyId = #{companyId} where id = #{id}")
    void update(Model entity);

    @Select("select * from carmanufacturing.model where id = #{id}")
    Model getById(int id);

    @Select("select * from carmanufacturing.model")
    List<Model> getAll();

    @Select("select * from carmanufacturing.model where CompanyId = #{id}")
    List<Model> getByCompanyId(int id);
}
