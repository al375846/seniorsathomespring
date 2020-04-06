package seniorsathome.seniorsathomespring.dao;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.InvoiceLine;
import seniorsathome.seniorsathomespring.model.Volunteer;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VolunteerRowMapper implements RowMapper<Volunteer> {
    public Volunteer mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        Volunteer v = new Volunteer();
        v.setIdNumber(rs.getString("idnumber"));
        v.setName(rs.getString("name"));
        v.setPhoneNumber(rs.getString("phonenumber"));
        v.setEmail(rs.getString("email"));
        v.setAddress(rs.getString("address"));
        v.setUserName(rs.getString("username"));
        v.setPassword(rs.getString("password"));
        v.setRequestDate(rs.getDate("requestdate").toLocalDate());
        Date fecha =rs.getDate("approvaldate");
        if (fecha != null) v.setApprovalDate(fecha.toLocalDate());
        else v.setApprovalDate(null);

        return v;
    }

}
