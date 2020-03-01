package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Beneficiary;
import seniorsathome.seniorsathomespring.model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InvoiceRowMapper implements RowMapper<Invoice> {

    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {

        Invoice invoice = new Invoice();

        invoice.setNumberID(rs.getString("number_id"));
        invoice.setReleaseDate(rs.getDate("release_date"));
        invoice.setStartDate(rs.getDate("start_date"));
        invoice.setFinalDate(rs.getDate("final_date"));
        invoice.setPrice(rs.getDouble("price"));
        invoice.setBeneficiaryID(rs.getString("beneficiary_id"));

        return invoice;
    }

}
