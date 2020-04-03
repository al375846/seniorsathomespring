package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Request;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class RequestRowMapper implements RowMapper<Request> {

    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
        Request request = new Request();
        request.setNumber_id(rs.getString("number_id"));
        request.setStatus(rs.getString("status"));
        request.setType(rs.getString("type"));
        request.setStart_date(rs.getDate("start_date").toLocalDate());
        request.setFinal_date(rs.getDate("final_date").toLocalDate());

        Date approvalDate = rs.getDate("approval_date");
        if(approvalDate != null) request.setApproval_date(approvalDate.toLocalDate());
        else request.setApproval_date(null);

        Date rejectDate = rs.getDate("reject_date");
        if(rejectDate != null) request.setReject_date(rejectDate.toLocalDate());
        else request.setApproval_date(null);

        request.setComments(rs.getString("comments"));
        request.setBeneficiary_id(rs.getString("beneficiary_id"));
        request.setContract_id(rs.getString("contract_id"));
        return request;
    }
}
