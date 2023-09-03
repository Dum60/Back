package hibernate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import shared.Company;

import java.sql.Date;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HibernateTest {
    private static CompanyDaoImpl companyDAO;
    private static shared.Company company;

    @BeforeAll
    static void setUp() {
        companyDAO = new CompanyDaoImpl();
        company = new shared.Company(0, "Pipi", new Date(103, 3, 11));
    }

    @Test
    @Order(1)
    void save() {
        companyDAO.Save(company);
        Assertions.assertEquals("Pipi", companyDAO.GetById(company.getId()).getName());
    }

    @Test
    @Order(2)
    void update() {
        company.setName("False");
        companyDAO.Update(company);
        Assertions.assertNotEquals("Test", companyDAO.GetById(company.getId()).getName());
    }

    @Test
    @Order(3)
    void deleteById() {
        companyDAO.DeleteById(company.getId());
        List<Company> companies = companyDAO.GetAll();
        Assertions.assertFalse(companies.contains(company));
    }
}
