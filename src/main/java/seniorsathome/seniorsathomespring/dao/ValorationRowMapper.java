package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;

import seniorsathome.seniorsathomespring.model.Valoration;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ValorationRowMapper  implements RowMapper<Valoration> {
    public Valoration mapRow(ResultSet rs, int rowNum) throws SQLException {

        Valoration valoration = new Valoration();

        valoration.setIdValoration(rs.getString("idValoration"));
        valoration.setRate(rs.getDouble("rate"));
        valoration.setComment(rs.getString("comment"));
        valoration.setIdVolunteer(rs.getString("idVolunteer"));
        valoration.setIdCompany(rs.getString("idCompany"));
        valoration.setIdBeneficiary(rs.getString("idBeneficiary"));

        return valoration;
    }
}
