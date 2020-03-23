package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import seniorsathome.seniorsathomespring.model.Company;
import seniorsathome.seniorsathomespring.model.Invoice;

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
