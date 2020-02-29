package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Beneficiary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BeneficiaryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*Añade un beneficiario a la base de datos */
    public void addBeneficiary (Beneficiary beneficiary) {
        jdbcTemplate.update("INSERT INTO Beneficiary VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)",
                beneficiary.getIdentificationNumber(), beneficiary.getName(), beneficiary.getSurnames(), beneficiary.getPhoneNumber(), beneficiary.getEmail(), beneficiary.getAddress(), beneficiary.getUserName(), beneficiary.getPassword(), beneficiary.getSocialWorkerID());
    }

    /*Elimina un beneficiario a la base de datos */
    public void deleteBeneficiary (Beneficiary beneficiary) {
        jdbcTemplate.update("DELETE FROM Beneficiary WHERE identificationNumber=?", beneficiary.getIdentificationNumber());
    }

    public void deleteBeneficiary (String idBeneficiary) {
        jdbcTemplate.update("DELETE FROM Beneficiary WHERE identificationNumber=?", idBeneficiary);
    }

    /*Modifica los datos de un beneficiario de la base de datos */
    public void updateBeneficiary (Beneficiary beneficiary) {
        jdbcTemplate.update("UPDATE Beneficiary SET name=?, surnames=?, phoneNumber=?, email=?, address=?, userName=?, password=?, socialWorkerID=?  WHERE identificationNumber=?", beneficiary.getName(), beneficiary.getSurnames(), beneficiary.getPhoneNumber(), beneficiary.getEmail(), beneficiary.getAddress(), beneficiary.getUserName(), beneficiary.getPassword(), beneficiary.getSocialWorkerID(), beneficiary.getIdentificationNumber());
    }

    /*Obetiene los un beneficiario a partir de su ID. Devuelve nulo si no existe. */
    public  Beneficiary getBeneficiary (String idBeneficiary){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Beneficiary WHERE identificationNumber=?",
                    new BeneficiaryRowMapper(),
                    idBeneficiary);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Obtiene una lista de todos los beneficiarios de la base de datos. Devuelve una lista vacia si no hay*/
    public List<Beneficiary> getBeneficiaries(){
        try{
            return jdbcTemplate.query("SELECT * FROM Beneficiary",
                    new BeneficiaryRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Beneficiary>();
        }
    }
}
