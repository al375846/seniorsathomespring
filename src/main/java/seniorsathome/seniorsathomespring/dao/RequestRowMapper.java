package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Request;
import seniorsathome.seniorsathomespring.model.RequestStatus;
import seniorsathome.seniorsathomespring.model.ServiceType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();
        request.setNumberID(rs.getString("numberID"));
        request.setStatus(RequestStatus.values()[rs.getInt("requestStatus")]);
        request.setServiceType(ServiceType.values()[rs.getInt("serviceType")]);
        request.setStartDate(rs.getDate("startDate"));
        request.setFinalDate(rs.getDate("finalDate"));
        request.setApprovalDate(rs.getDate("approvalDate"));
        request.setRejectDate(rs.getDate("rejectDate"));
        request.setComments(rs.getString("comments"));
        request.setBeneficiaryID(rs.getString("beneficiaryID"));
        request.setContractID(rs.getString("contractID"));
        return request;
    }
}
