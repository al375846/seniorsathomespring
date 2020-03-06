package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.VolunteersSchedule;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VolunteersScheduleRowMapper implements RowMapper<VolunteersSchedule> {
    public VolunteersSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {

        VolunteersSchedule s = new VolunteersSchedule();

        s.setNumberID(rs.getString("numberID"));
        s.setDay(rs.getDate("day"));
        s.setStartHour(rs.getTime("startHour"));
        s.setFinalHour(rs.getTime("finalHour"));
        s.setStatus(rs.getByte("status"));
        s.setBeneficiaryID(rs.getString("beneficiaryID"));
        s.setVolunteerID(rs.getString("volunteerID"));

        return s;
    }
}
