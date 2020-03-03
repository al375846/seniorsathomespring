package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.InvoiceLine;
import seniorsathome.seniorsathomespring.model.Volunteer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VolunteerRowMapper implements RowMapper<Volunteer> {
    public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {

        Volunteer v = new Volunteer();
        v.setIdNumber(rs.getString("idNumber"));
        v.setName(rs.getString("name"));
        v.setPhoneNumber(rs.getString("phoneNumber"));
        v.setEmail(rs.getString("email"));
        v.setAddress(rs.getString("address"));
        v.setUserName(rs.getString("userName"));
        v.setPassword(rs.getString("password"));
        v.setRequestDate(rs.getDate("requestDate"));
        v.setApprovalDate(rs.getDate("approvalDate"));

        return v;
    }

}