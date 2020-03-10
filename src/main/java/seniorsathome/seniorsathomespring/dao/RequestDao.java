package seniorsathome.seniorsathomespring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import seniorsathome.seniorsathomespring.model.Request;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addRequest (Request request) {
        jdbcTemplate.update("INSERT INTO Request VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                request.getNumberID(), request.getStatus(), request.getServiceType(), request.getStartDate(),
                request.getFinalDate(), request.getApprovalDate(), request.getRejectDate(), request.getComments(),
                request.getBeneficiaryID(), request.getContractID());
    }

    public void deleteRequest (Request request) {
        jdbcTemplate.update("DELETE FROM Request WHERE number_id=?", request.getNumberID());
    }

    public void deleteRequest (String numberID) {
        jdbcTemplate.update("DELETE FROM Request WHERE number_id=?", numberID);
    }

    public void updateRequest (Request request) {
        jdbcTemplate.update("UPDATE Request SET status=?, type=?, start_date=?, final_date=?, approval_date=?, reject_date=?, comments=?, beneficiary_id=?, contract_id=? WHERE number_id=?",
                request.getStatus(), request.getServiceType(), request.getStartDate(),
                request.getFinalDate(), request.getApprovalDate(), request.getRejectDate(), request.getComments(),
                request.getBeneficiaryID(), request.getContractID(), request.getNumberID());
    }

    public  Request getRequest (String numberID){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE number_id=?",
                    new RequestRowMapper(),
                    numberID);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getRequests(){
        try{
            return jdbcTemplate.query("SELECT * FROM Request",
                    new RequestRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
}

