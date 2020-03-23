package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Beneficiary;
import seniorsathome.seniorsathomespring.model.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class InvoiceRowMapper implements RowMapper<Invoice> {

    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {

        Invoice invoice = new Invoice();

        invoice.setNumberID(rs.getString("number_id"));
        invoice.setReleaseDate(rs.getObject("release_date", LocalDate.class));
        invoice.setStartDate(rs.getObject("start_date", LocalDate.class));
        invoice.setFinalDate(rs.getObject("final_date", LocalDate.class));
        invoice.setPrice(rs.getDouble("price"));
        invoice.setBeneficiaryID(rs.getString("beneficiary_id"));

        return invoice;
    }

}
