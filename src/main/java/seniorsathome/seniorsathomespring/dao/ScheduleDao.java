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
        if(schedule.getStatus()==false) {
            jdbcTemplate.update("INSERT INTO Volunteersschedule VALUES(?, ?, ?, ?, CAST(0 AS BIT ), ?, ?)",
                    schedule.getNumberid(), schedule.getDay(), schedule.getStarthour(), schedule.getFinalhour(),
                    schedule.getBeneficiaryid(), schedule.getVolunteerid());
        }else{
            jdbcTemplate.update("INSERT INTO Volunteersschedule VALUES(?, ?, ?, ?, CAST(1 AS BIT ), ?, ?)",
                    schedule.getNumberid(), schedule.getDay(), schedule.getStarthour(), schedule.getFinalhour(),
                    schedule.getBeneficiaryid(), schedule.getVolunteerid());
        }
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

    public List<Schedule> getSchedules(){
        try{
            return jdbcTemplate.query("SELECT * FROM Volunteersschedule",
                    new ScheduleRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Schedule>();
        }
    }
}
