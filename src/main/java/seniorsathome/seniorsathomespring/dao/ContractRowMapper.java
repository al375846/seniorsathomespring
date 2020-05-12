package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Contract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ContractRowMapper implements RowMapper<Contract> {

    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {

        Contract contract = new Contract();

        contract.setNumberID(rs.getString("numberID"));
        contract.setServiceType(rs.getString("serviceType"));
        contract.setQuantity(rs.getInt("quantity"));
        contract.setStartDate(rs.getDate("startDate").toLocalDate());
        contract.setFinalDate(rs.getDate("finalDate").toLocalDate());
        contract.setCompanyID(rs.getString("companyID"));

        return contract;
    }
}
