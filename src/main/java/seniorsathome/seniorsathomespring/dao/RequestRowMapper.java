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
        request.setNumber_id(rs.getString("number_id"));
        request.setStatus(rs.getString("status"));
        request.setType(rs.getString("type"));
        request.setStart_date(rs.getDate("start_date"));
        request.setFinal_date(rs.getDate("final_date"));
        request.setApproval_date(rs.getDate("approval_date"));
        request.setReject_date(rs.getDate("reject_date"));
        request.setComments(rs.getString("comments"));
        request.setBeneficiary_id(rs.getString("beneficiary_id"));
        request.setContract_id(rs.getString("contract_id"));
        return request;
    }
}
