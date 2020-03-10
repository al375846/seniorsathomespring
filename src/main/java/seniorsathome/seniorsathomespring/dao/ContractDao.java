package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.ServiceType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ContractDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*Añade un contrato a la base de datos*/
    public void addContract(Contract contract) {
        jdbcTemplate.update("INSERT INTO Contract VALUES (?,?,?,?,?,?,?)",
                contract.getNumberID(), contract.getServiceType(), contract.getQuantity(), contract.getStartDate(), contract.getFinalDate(), contract.getPrice(), contract.getCompanyID());
    }

    /*Elimina un contrato de la base de datos*/
    public void deleteContract(Contract contract) {
        jdbcTemplate.update("DELETE FROM Contract WHERE numberID=?",
                contract.getNumberID());
    }

    public void deleteContract(String numberID) {
        jdbcTemplate.update("DELETE FROM Contract WHERE numberID=?",
                numberID);
    }

    /*Modifica los datos de un contrato en la base de datos*/
    public void updateContract(Contract contract) {
        jdbcTemplate.update("UPDATE Company SET serviceType=?, quantity=?, startDate=?, finalDate=?, price=?, companyID=? WHERE numberID=?",
                contract.getServiceType(), contract.getQuantity(), contract.getStartDate(), contract.getFinalDate(), contract.getPrice(), contract.getCompanyID(), contract.getNumberID());
    }

    /*Obtiene un contrato a partir de su numberID. Si no existe devuelve null*/
    public Contract getContract(String numberID) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Contract WHERE numberID=?",
                    new ContractRowMapper(),
                    numberID);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Obtiene una lista de todos los contratos de la base de datos. Devuelve una lista vacia si no hay*/
    public List<Contract> getContracts(){
        try{
            return jdbcTemplate.query("SELECT * FROM Contract",
                    new ContractRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }
}
