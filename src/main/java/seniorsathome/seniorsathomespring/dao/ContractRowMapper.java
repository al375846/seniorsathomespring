package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Contract;
import seniorsathome.seniorsathomespring.model.ServiceType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractRowMapper implements RowMapper<Contract> {

    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {

        Contract contract = new Contract();

        contract.setNumberID(rs.getString("numberID"));
        contract.setServiceType(ServiceType.valueOf(rs.getString("serviceType")));
        contract.setQuantity(rs.getInt("quantity"));
        contract.setStartDate(rs.getDate("startDate"));
        contract.setFinalDate(rs.getDate("finalDate"));
        contract.setPrice(rs.getDouble("price"));
        contract.setCompanyID(rs.getString("companyID"));

        return contract;
    }
}
