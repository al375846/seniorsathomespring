package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Schedule;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {

    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {

        Schedule schedule = new Schedule();

        schedule.setNumberid(rs.getString("numberid"));
        schedule.setDay(rs.getDate("day").toLocalDate());
        schedule.setStarthour(rs.getTime("starthour").toLocalTime());
        schedule.setFinalhour(rs.getTime("finalhour").toLocalTime());
        schedule.setStatus(rs.getBoolean("status"));
        schedule.setBeneficiaryid(rs.getString("beneficiaryid"));
        schedule.setVolunteerid(rs.getString("volunteerid"));

        return schedule;
    }
}
