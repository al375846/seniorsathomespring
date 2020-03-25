package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;

import seniorsathome.seniorsathomespring.model.Valoration;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ValorationRowMapper  implements RowMapper<Valoration> {
    public Valoration mapRow(ResultSet rs, int rowNum) throws SQLException {

        Valoration valoration = new Valoration();

        valoration.setIdValoration(rs.getString("idvaloration"));
        valoration.setRate(rs.getDouble("rate"));
        valoration.setComment(rs.getString("comment"));
        valoration.setIdVolunteer(rs.getString("idvolunteer"));
        valoration.setIdCompany(rs.getString("idcompany"));
        valoration.setIdBeneficiary(rs.getString("idbeneficiary"));

        return valoration;
    }
}
