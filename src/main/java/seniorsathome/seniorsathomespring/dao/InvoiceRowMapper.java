package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Beneficiary;
import seniorsathome.seniorsathomespring.model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InvoiceRowMapper implements RowMapper<Invoice> {

    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {

        Invoice invoice = new Invoice();

        invoice.setNumberID(rs.getString("numberId"));
        invoice.setReleaseDate(rs.getDate("releaseDate"));
        invoice.setStartDate(rs.getDate("startDate"));
        invoice.setFinalDate(rs.getDate("finalDate"));
        invoice.setPrice(rs.getDouble("price"));
        invoice.setBeneficiaryID(rs.getString("beneficiaryId"));

        return invoice;
    }

}
