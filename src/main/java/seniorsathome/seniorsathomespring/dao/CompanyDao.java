package seniorsathome.seniorsathomespring.dao;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Company;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*Añade una compañia a la base de datos*/
    public void addCompany(Company company) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        jdbcTemplate.update("INSERT INTO Company VALUES (?,?,?,?,?,?,?,?,?)",
                company.getFiscalNumber(), company.getName(), company.getEmail(), company.getResponsibleName(), company.getResponsiblePhoneNumber(), company.getResponsibleAddress(), company.getPhoneNumber(), company.getUserName(), passwordEncryptor.encryptPassword(company.getPassword()));
    }

    /*Elimina una compañia de la base de datos*/
    public void deleteCompany(String fiscalNumber) {
        jdbcTemplate.update("DELETE FROM Company WHERE fiscalNumber=?",
                fiscalNumber);
    }

    /*Modifica los datos de una compañia en la base de datos*/
    public void updateCompany(Company company) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        jdbcTemplate.update("UPDATE Company SET name=?, email=?, responsibleName=?, responsiblePhoneNumber=?, responsibleAddress=?, phoneNumber=?, userName=?, password=? WHERE fiscalNumber=?",
                company.getName(), company.getEmail(), company.getResponsibleName(), company.getResponsiblePhoneNumber(), company.getResponsibleAddress(), company.getPhoneNumber(), company.getUserName(), passwordEncryptor.encryptPassword(company.getPassword()), company.getFiscalNumber());
    }

    /*Obtiene una compañia a partir de su fiscalNumber. Si no existe devuelve null*/
    public Company getCompany(String fiscalNumber) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE fiscalNumber=? ",
                    new CompanyRowMapper(),
                    fiscalNumber);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Obtiene una compañia a partir de su nombre. Si no existe devuelve null*/
    public Company getCompanyByName(String nameCompany) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE name=? ",
                    new CompanyRowMapper(),
                    nameCompany);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Obtiene una lista de todas las compañias de la base de datos. Devuelve una lista vacia si no hay*/
    public List<Company> getCompanies(){
        try{
            return jdbcTemplate.query("SELECT * FROM Company WHERE fiscalNumber<>''" ,
                    new CompanyRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Company>();
        }
    }

    /*Obtiene una compañia a partir de su nombre de usuario. Si no existe devuelve null*/
    public Company getCompanyByUserName(String nameCompany) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Company WHERE userName=? ",
                    new CompanyRowMapper(),
                    nameCompany);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
