package seniorsathome.seniorsathomespring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Valoration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ValorationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*Añade una valoración a la base de datos*/
    public void addValoration(Valoration valoration) {
        jdbcTemplate.update("INSERT INTO Valoration VALUES (?,?,?,?,?,?)",
                "N"+(getValorations().size() + 1), valoration.getRate(), valoration.getComment(), valoration.getIdVolunteer(), valoration.getIdCompany(), valoration.getIdBeneficiary());
    }

    /*Elimina una valoración de la base de datos*/
    public void deleteValoration(Valoration valoration) {
        jdbcTemplate.update("DELETE FROM Valoration WHERE idvaloration=?",
                valoration.getIdValoration());
    }

    public void deleteValoration(String idValoration) {
        jdbcTemplate.update("DELETE FROM Valoration WHERE idvaloration=?",
                idValoration);
    }

    /*Modifica los datos de una valoración en la base de datos*/
    public void updateValoration(Valoration valoration) {
        jdbcTemplate.update("UPDATE Valoration SET rate=?, comment=?, idvolunteer=?, idcompany=?, idbeneficiary=? WHERE idvaloration=?",
                valoration.getRate(), valoration.getComment(), valoration.getIdVolunteer(), valoration.getIdCompany(), valoration.getIdBeneficiary(), valoration.getIdValoration());
    }

    /*Obtiene una valoracion a partir de su numero de identificación. Si no existe devuelve null*/
    public Valoration getValoration(String idValoration) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Valoration WHERE idvaloration=?",
                    new ValorationRowMapper(),
                    idValoration);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Obtiene una lista de todas las valoraciones de la base de datos. Devuelve una lista vacia si no hay*/
    public List<Valoration> getValorations(){
        try{
            return jdbcTemplate.query("SELECT * FROM Valoration",
                    new ValorationRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Valoration>();
        }
    }

    public List<Valoration> getVolunteerValorations(String volunteerid){
        try{
            return jdbcTemplate.query("SELECT * FROM Valoration WHERE idVolunteer=?",
                    new ValorationRowMapper(),volunteerid);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Valoration>();
        }
    }

    public Integer getVolunteerAverage(String volunteerid){
        try{
            return jdbcTemplate.queryForObject("SELECT AVG(rate) FROM Valoration WHERE idVolunteer=?",
                    Integer.class, volunteerid);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Valoration> getCompanyValorations(String volunteerid){
        try{
            return jdbcTemplate.query("SELECT * FROM Valoration WHERE idCompany=?",
                    new ValorationRowMapper(),volunteerid);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Valoration>();
        }
    }

    public Integer getCompanyAverage(String volunteerid){
        try{
            return jdbcTemplate.queryForObject("SELECT AVG(rate) FROM Valoration WHERE idCompany=?",
                    Integer.class, volunteerid);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
