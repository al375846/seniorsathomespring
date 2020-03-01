package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.InvoiceLine;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceLineDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInvoiceLine (InvoiceLine invoiceLine) {
        jdbcTemplate.update("INSERT INTO InvoiceLine VALUES(?, ?, ?, ?)",
                invoiceLine.getNumberID(), invoiceLine.getPrice(),
                invoiceLine.getRequestID(), invoiceLine.getInvoiceID());
    }

    public void deleteInvoiceLine (InvoiceLine invoiceLine) {
        jdbcTemplate.update("DELETE FROM InvoiceLine WHERE number_id=?", invoiceLine.getNumberID());
    }

    public void updateInvoiceLine (InvoiceLine invoiceLine) {
        jdbcTemplate.update("UPDATE InvoiceLine SET number_id=?, price=?, request_id=?, invoice_id=?",
                invoiceLine.getNumberID(), invoiceLine.getPrice(), invoiceLine.getRequestID(),
                invoiceLine.getInvoiceID());
    }

    public  InvoiceLine getInvoiceLine (String numberID){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM InvoiceLine WHERE number_id=?",
                    new InvoiceLineRowMapper(),
                    numberID);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<InvoiceLine> getInvoiceLines(){
        try{
            return jdbcTemplate.query("SELECT * FROM InvoiceLine",
                    new InvoiceLineRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<InvoiceLine>();
        }
    }

}
