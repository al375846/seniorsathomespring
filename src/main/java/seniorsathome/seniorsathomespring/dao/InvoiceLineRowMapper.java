package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Invoice;
import seniorsathome.seniorsathomespring.model.InvoiceLine;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class InvoiceLineRowMapper implements RowMapper<InvoiceLine> {

    public InvoiceLine mapRow(ResultSet rs, int rowNum) throws SQLException {

        InvoiceLine invoiceLine = new InvoiceLine();

        invoiceLine.setNumberID(rs.getString("number_id"));
        invoiceLine.setPrice(rs.getDouble("price"));
        invoiceLine.setRequestID(rs.getString("request_id"));
        invoiceLine.setInvoiceID(rs.getString("invoice_id"));

        return invoiceLine;
    }

}
