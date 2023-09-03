package jDBC;

import lombok.NoArgsConstructor;
import shared.Company;
import shared.DBExceptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CompanyDaoImpl implements shared.CompanyDAO {
    @Override
    public Company Save(Company entity) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO \"CarManufacturing\".\"Company\" VALUES (default, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, entity.getName());
                statement.setDate(2, entity.getCreationDate());
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                }
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
                statement.execute(String.format("DELETE FROM \"CarManufacturing\".\"Company\" WHERE id = %d", id));
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public void DeleteAll() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeQuery("DELETE FROM \"CarManufacturing\".\"Company\"");
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
    }

    @Override
    public Company Update(Company entity) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE \"CarManufacturing\".\"Company\" SET name = ?, creationdate = ? WHERE id = ?")) {
                statement.setString(1, entity.getName());
                statement.setDate(2, entity.getCreationDate());
                statement.setInt(3, entity.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return entity;
    }

    @Override
    public Company GetById(int id) {
        Company company = new Company();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM \"CarManufacturing\".\"Company\" WHERE id = %d", id))) {
                    resultSet.next();
                    company.setId(resultSet.getInt("id"));
                    company.setName(resultSet.getString("Name"));
                    company.setCreationDate(resultSet.getDate("CreationDate"));
                }
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return company;
    }

    @Override
    public List<Company> GetAll() {
        List<Company> companies = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "root", "root")) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("SELECT * FROM \"CarManufacturing\".\"Company\"")) {
                    while (resultSet.next()) {
                        Company company = new Company();
                        company.setId(resultSet.getInt("id"));
                        company.setName(resultSet.getString("Name"));
                        company.setCreationDate(resultSet.getDate("CreationDate"));
                        companies.add(company);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DBExceptions(e.getMessage());
        }
        return companies;
    }
}
