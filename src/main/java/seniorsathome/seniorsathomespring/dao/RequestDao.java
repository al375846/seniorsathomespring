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

    /*Añadir una nueva solicitud*/
    public void addRequest (Request request) {
        jdbcTemplate.update("INSERT INTO Request VALUES(?, ?::STATUSTYPE, ?::SERVICETYPE, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                request.getNumber_id(), request.getStatus(), request.getType(), request.getStart_date(),
                request.getFinal_date(), request.getApproval_date(), request.getReject_date(), request.getComments(),
                request.getBeneficiary_id(), request.getContract_id(), request.getDays(), request.getStarthour());
    }

    /*Eliminar solicitud*/
    public void deleteRequest (String numberID) {
        jdbcTemplate.update("DELETE FROM Request WHERE number_id=?", numberID);
    }

    /*Actualizar solicitud*/
    public void updateRequest (Request request) {
        jdbcTemplate.update("UPDATE Request SET status=?::STATUSTYPE, type=?::SERVICETYPE, start_date=?, final_date=?, approval_date=?, reject_date=?, comments=?, beneficiary_id=?, contract_id=?, days=?, starthour=? WHERE number_id=?",
                request.getStatus(), request.getType(), request.getStart_date(),
                request.getFinal_date(), request.getApproval_date(), request.getReject_date(), request.getComments(),
                request.getBeneficiary_id(), request.getContract_id(), request.getDays(), request.getStarthour(), request.getNumber_id());
    }

    /*Mostrar solicitud según su ID*/
    public  Request getRequest (String number_id){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Request WHERE number_id=?",
                    new RequestRowMapper(),
                    number_id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Lista de todas las solicitudes*/
    public List<Request> getRequests(){
        try{
            return jdbcTemplate.query("SELECT * FROM Request",
                    new RequestRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    /*Lista de las solicitudes pendientes*/
    public List<Request> listUnsolvedRequests() {
        try{
            return jdbcTemplate.query("SELECT * FROM Request WHERE status='UNSOLVED'",
                    new RequestRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    /*Lista de las solicitudes aprobadas de un contrato*/
    public List<Request> listRequestByContractId(String contractID) {
        try{
            return jdbcTemplate.query("SELECT * FROM Request WHERE status='APPROVED' AND contract_id=?",
                    new RequestRowMapper(),
                    contractID);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }
}

