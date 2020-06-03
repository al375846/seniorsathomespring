package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.ServiceType;

import javax.sql.DataSource;
import java.time.Clock;
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
        jdbcTemplate.update("INSERT INTO Contract VALUES(?,?::SERVICETYPE,?,?,?,?)",
                "CT"+Integer.toString(getContracts().size() + 1), contract.getServiceType(), contract.getQuantity(), contract.getStartDate(), contract.getFinalDate(), contract.getCompanyID());
    }

    /*Elimina un contrato de la base de datos*/
    public void deleteContract(String numberID) {
        jdbcTemplate.update("DELETE FROM Contract WHERE numberID=?",
                numberID);
    }

    /*Modifica los datos de un contrato en la base de datos*/
    public void updateContract(Contract contract) {
        jdbcTemplate.update("UPDATE Contract SET serviceType=?::SERVICETYPE, quantity=?, startDate=?, finalDate=?, companyID=? WHERE numberID=?",
                contract.getServiceType(), contract.getQuantity(), contract.getStartDate(), contract.getFinalDate(), contract.getCompanyID(), contract.getNumberID());
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
            return jdbcTemplate.query("SELECT * FROM Contract WHERE numberID<>''",
                    new ContractRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }

    /*Obtiene una lista de todos los contratos de una compañia de la base de datos. Devuelve una lista vacia si no hay*/
    public List<Contract> getContractsCompany(String nombre){
        try{
            return jdbcTemplate.query("SELECT * FROM Contract WHERE companyID=?",
                    new ContractRowMapper(),nombre);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }

    /*Obtiene una lista de todos los contratos de un servicio de la base de datos. Devuelve una lista vacia si no hay*/
    public List<Contract> getContractsByService(String servicename){
        try{
            return jdbcTemplate.query("SELECT * FROM Contract WHERE numberID<>'' and serviceType=?::SERVICETYPE and quantity>0 and startDate>=CURRENT_DATE",
                    new ContractRowMapper(), servicename);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Contract>();
        }
    }

}
