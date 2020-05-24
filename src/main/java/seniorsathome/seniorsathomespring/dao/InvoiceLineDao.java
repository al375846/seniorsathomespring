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

    /*Añadir linea de factura*/
    public void addInvoiceLine (InvoiceLine invoiceLine) {
        jdbcTemplate.update("INSERT INTO InvoiceLine VALUES(?, ?, ?, ?)",
                invoiceLine.getNumberID(), invoiceLine.getPrice(),
                invoiceLine.getRequestID(), invoiceLine.getInvoiceID());
    }

    /*Eliminar linea de factura*/
    public void deleteInvoiceLine (String number_id) {
        jdbcTemplate.update("DELETE FROM InvoiceLine WHERE number_id=?", number_id);
    }

    /*Actualizar linea de factura*/
    public void updateInvoiceLine (InvoiceLine invoiceLine) {
        jdbcTemplate.update("UPDATE InvoiceLine SET price=?, request_id=?, invoice_id=? WHERE number_id=?",
                invoiceLine.getPrice(), invoiceLine.getRequestID(), invoiceLine.getInvoiceID(), invoiceLine.getNumberID());
    }

    /*Obtener linea de factura según ID*/
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

    /*Obtener todas las lineas de factura*/
    public List<InvoiceLine> getInvoiceLines(){
        try{
            return jdbcTemplate.query("SELECT * FROM InvoiceLine",
                    new InvoiceLineRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<InvoiceLine>();
        }
    }

    /*Listar todas las lineas de factura de una factura*/
    public List<InvoiceLine> getInvoiceLinesByInvoice(String invoice_id){
        try{
            return jdbcTemplate.query("SELECT * FROM InvoiceLine WHERE invoice_id=?",
                    new InvoiceLineRowMapper(), invoice_id);
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<InvoiceLine>();
        }
    }

}
