package seniorsathome.seniorsathomespring.dao;

import seniorsathome.seniorsathomespring.model.Beneficiary;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BeneficiaryRowMapper implements RowMapper<Beneficiary> {

    public Beneficiary mapRow(ResultSet rs, int rowNum) throws SQLException {

        Beneficiary beneficiary = new Beneficiary();

        beneficiary.setIdentificationNumber(rs.getString("identificationNumber"));
        beneficiary.setName(rs.getString("name"));
        beneficiary.setSurnames(rs.getString("surnames"));
        beneficiary.setPhoneNumber(rs.getString("phoneNumber"));
        beneficiary.setEmail(rs.getString("email"));
        beneficiary.setAddress(rs.getString("address"));
        beneficiary.setUserName(rs.getString("userName"));
        beneficiary.setPassword(rs.getString("password"));
        beneficiary.setSocialWorkerID(rs.getString("socialWorkerID"));

        return beneficiary;
    }
}
