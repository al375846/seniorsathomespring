package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyRowMapper implements RowMapper<Company> {

    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {

        Company company = new Company();

        company.setFiscalNumber(rs.getString("fiscalNumber"));
        company.setName(rs.getString("name"));
        company.setEmail(rs.getString("email"));
        company.setResponsibleName(rs.getString("responsibleName"));
        company.setResponsiblePhoneNumber(rs.getString("responsiblePhoneNumber"));
        company.setResponsibleAddress(rs.getString("responsibleAddress"));
        company.setPhoneNumber(rs.getString("phoneNumber"));
        company.setUserName(rs.getString("userName"));
        company.setPassword(rs.getString("password"));

        return company;
    }
}
