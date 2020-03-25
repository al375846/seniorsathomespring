package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Invoice;
import seniorsathome.seniorsathomespring.model.Volunteer;
import seniorsathome.seniorsathomespring.dao.VolunteerRowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VolunteerDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVolunteer (Volunteer v) {
        jdbcTemplate.update("INSERT INTO Volunteer VALUES(?,?,?, ?,?,?, ?,?,?)",
                v.getIdNumber(),v.getName(),v.getPhoneNumber(),v.getEmail(),v.getAddress(),
                v.getUserName(),v.getPassword(),v.getRequestDate(),v.getApprovalDate());
    }
    public void deleteVolunteer(String idNumber){
        jdbcTemplate.update("DELETE FROM Volunteer WHERE idnumber=?", idNumber);
    }

    public void updateVolunteer(Volunteer v) {
        jdbcTemplate.update("UPDATE Volunteer SET name=?,phonenumber=?,email=?,address=?,username=?,password=?,requestdate=?,approvaldate=? WHERE idnumber=?",
                v.getName(),v.getPhoneNumber(),v.getEmail(),v.getAddress(),
                v.getUserName(),v.getPassword(),v.getRequestDate(),v.getApprovalDate(),v.getIdNumber());
    }


    public  Volunteer getVolunteer (String idNumber){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE idnumber=?",
                    new VolunteerRowMapper(),
                    idNumber);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Volunteer> getVolunteers(){
        try{
            return jdbcTemplate.query("SELECT * FROM Volunteer",
                    new VolunteerRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }
    }
}
