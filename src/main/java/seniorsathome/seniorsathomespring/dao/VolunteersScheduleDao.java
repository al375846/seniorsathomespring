package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.VolunteersSchedule;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VolunteersScheduleDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addVolunteersSchedule (VolunteersSchedule s) {
        jdbcTemplate.update("INSERT INTO VolunteersSchedule VALUES(?,?,?,?, ?,?,?)",s.getNumberID(),s.getDay(),s.getStartHour(),
        s.getFinalHour(),s.getStatus(),s.getBeneficiaryID(),s.getVolunteerID());
    }

    public void updateVolunteersSchedule(VolunteersSchedule v) {
        jdbcTemplate.update("UPDATE Volunteer SET  startHour=?, finalHour=? , status=? , beneficiaryID=? , volunteerID=? WHERE numberID=?",
                v.getStartHour(),v.getFinalHour(),v.getStatus(),v.getBeneficiaryID(),v.getVolunteerID(),v.getNumberID()
                );
    }
    public void deleteVolunteersSchedule(String s){
        jdbcTemplate.update("DELETE FROM VolunteersSchedule WHERE idNumber=?", s);
    }


    public  VolunteersSchedule getVolunteersSchedule (String idNumber){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM VolunteersSchedule WHERE idNumber=?",
                    new VolunteersScheduleRowMapper(),
                    idNumber);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<VolunteersSchedule> getVolunteersSchedules(){
        try{
            return jdbcTemplate.query("SELECT * FROM VolunteersSchedule",
                    new VolunteersScheduleRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<VolunteersSchedule>();
        }
    }

}
