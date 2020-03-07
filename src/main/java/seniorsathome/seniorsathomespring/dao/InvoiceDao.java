package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Invoice;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InvoiceDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInvoice (Invoice invoice) {
        jdbcTemplate.update("INSERT INTO Invoice VALUES(?, ?, ?, ?, ?, ?)",
                invoice.getNumberID(), invoice.getReleaseDate(), invoice.getStartDate(),
                invoice.getFinalDate(), invoice.getPrice(), invoice.getBeneficiaryID());
    }

    public void deleteInvoice (String numberID) {
        jdbcTemplate.update("DELETE FROM Invoice WHERE number_id=?", numberID);
    }

    public void updateInvoice (Invoice invoice) {
        jdbcTemplate.update("UPDATE Invoice SET number_id=?, release_date=?, start_date=?, final_date=?, price=?, beneficiary_id=? WHERE number_id=?", invoice.getNumberID(),
                invoice.getReleaseDate(), invoice.getStartDate(), invoice.getFinalDate(), invoice.getPrice(),
                invoice.getBeneficiaryID(), invoice.getNumberID());
    }

    public  Invoice getInvoice (String numberID){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Invoice WHERE number_id=?",
                    new InvoiceRowMapper(),
                    numberID);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Invoice> getInvoices(){
        try{
            return jdbcTemplate.query("SELECT * FROM Invoice",
                    new InvoiceRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Invoice>();
        }
    }

}
