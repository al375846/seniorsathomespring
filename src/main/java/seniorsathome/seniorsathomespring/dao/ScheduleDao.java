package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Schedule;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSchedule (Schedule schedule) {
            jdbcTemplate.update("INSERT INTO Volunteersschedule VALUES(?, ?, ?, ?, CAST(0 AS BIT ), ?, ?)",
                    "S"+(getSchedules().size()+1), schedule.getDay(), schedule.getStarthour(), schedule.getFinalhour(),
                    "", schedule.getVolunteerid());

    }

    public void deleteSchedule (Schedule schedule) {
        jdbcTemplate.update("DELETE FROM Volunteersschedule WHERE numberid=?", schedule.getNumberid());
    }

    public void deleteSchedule (String numberID) {
        jdbcTemplate.update("DELETE FROM Volunteersschedule WHERE numberid=?", numberID);
    }

    public void updateSchedule (Schedule schedule) {
        if(schedule.getStatus()){
            jdbcTemplate.update("UPDATE Volunteersschedule SET day=?, starthour=?, finalhour=?, status=CAST(1 AS BIT ), beneficiaryid=?, volunteerid=? WHERE numberid=?",
                    schedule.getDay(), schedule.getStarthour(), schedule.getFinalhour(),
                     schedule.getBeneficiaryid(), schedule.getVolunteerid(), schedule.getNumberid());
        }else {
            jdbcTemplate.update("UPDATE Volunteersschedule SET day=?, starthour=?, finalhour=?, status=CAST(0 AS BIT ), beneficiaryid=?, volunteerid=? WHERE numberid=?",
                    schedule.getDay(), schedule.getStarthour(), schedule.getFinalhour(),
                     schedule.getBeneficiaryid(), schedule.getVolunteerid(), schedule.getNumberid());
        }
    }

    public  Schedule getSchedule (String number_id){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteersschedule WHERE numberid=?",
                    new ScheduleRowMapper(),
                    number_id);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public  List<Schedule> getScheduleByIdVolunteer (String number_id){
        try{
            return jdbcTemplate.query("SELECT * FROM Volunteersschedule where volunteerId=? ORDER BY day DESC",
                    new ScheduleRowMapper(),number_id);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Schedule>();
        }
    }

    public List<Schedule> getSchedules(){
        try{
            return jdbcTemplate.query("SELECT * FROM Volunteersschedule ",
                    new ScheduleRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Schedule>();
        }
    }

    public List<Schedule> getActiveSchedulesByVolunteer(String volunteerid){
        try{
            return jdbcTemplate.query("SELECT * FROM Volunteersschedule WHERE status=CAST(1 AS BIT) AND volunteerId=?",
                    new ScheduleRowMapper(), volunteerid);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Schedule>();
        }
    }
}
