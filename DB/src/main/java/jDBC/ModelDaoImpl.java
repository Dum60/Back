package jDBC;

import shared.DBExceptions;
import shared.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModelDaoImpl implements shared.ModelDAO {
    @Override
    public Model Save(Model entity) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(String.format("INSERT INTO \"Model\" VALUES (default, %s, %d, %d, %s, %d)",
                        entity.getName(),
                        entity.getLength(),
                        entity.getWidth(),
                        entity.getBody(),
                        entity.getCompanyId()));
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return entity;
    }

    @Override
    public void DeleteById(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(String.format("DELETE FROM \"Model\" WHERE id = %d", id));
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public void DeleteAll() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery("DELETE FROM \"Model\"");
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public Model Update(Model entity) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery(String.format("UPDATE \"Company\" SET Name = %s, Length = %d, Width = %d, Body = %s, CompanyId = %d WHERE id = %d",
                        entity.getName(),
                        entity.getLength(),
                        entity.getWidth(),
                        entity.getBody(),
                        entity.getCompanyId(),
                        entity.getId()));
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return entity;
    }

    @Override
    public Model GetById(int id) {
        Model model = new Model();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM \"Model\" WHERE id = %d", id))) {
                    model.setId(resultSet.getInt("id"));
                    model.setName(resultSet.getString("Name"));
                    model.setLength(resultSet.getInt("Length"));
                    model.setWidth(resultSet.getInt("Width"));
                    model.setBody(resultSet.getString("Body"));
                    model.setCompanyId(resultSet.getInt("CompanyId"));
                }
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return model;
    }

    @Override
    public List<Model> GetAll() {
        List<Model> models = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM \"Model\"")) {
                    while (resultSet.next()) {
                        Model model = new Model();
                        model.setId(resultSet.getInt("id"));
                        model.setName(resultSet.getString("Name"));
                        model.setLength(resultSet.getInt("Length"));
                        model.setWidth(resultSet.getInt("Width"));
                        model.setBody(resultSet.getString("Body"));
                        model.setCompanyId(resultSet.getInt("CompanyId"));
                        models.add(model);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return models;
    }

    @Override
    public List<Model> GetAllByCompanyId(int id) {
        List<Model> models = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM \"Model\" WHERE CompanyId = %d", id))) {
                    while (resultSet.next()) {
                        Model model = new Model();
                        model.setId(resultSet.getInt("id"));
                        model.setName(resultSet.getString("Name"));
                        model.setLength(resultSet.getInt("Length"));
                        model.setWidth(resultSet.getInt("Width"));
                        model.setBody(resultSet.getString("Body"));
                        model.setCompanyId(resultSet.getInt("CompanyId"));
                        models.add(model);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return models;
    }
}
