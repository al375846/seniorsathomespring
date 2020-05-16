package seniorsathome.seniorsathomespring.dao;


import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Beneficiary;
import seniorsathome.seniorsathomespring.model.Request;

import javax.sql.DataSource;
import java.time.LocalDate;
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
    public void addBeneficiary(Beneficiary beneficiary) {
        int numero = conseguirNumero();
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        jdbcTemplate.update("INSERT INTO Beneficiary VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                "B" + numero, beneficiary.getName(), beneficiary.getSurnames(), beneficiary.getPhoneNumber(), beneficiary.getEmail(), beneficiary.getAddress(), beneficiary.getUserName(), passwordEncryptor.encryptPassword(beneficiary.getPassword()), beneficiary.getSocialWorkerID(), beneficiary.getAccount());
    }

    /*Elimina un beneficiario a la base de datos */
    public void deleteBeneficiary(Beneficiary beneficiary) {
        jdbcTemplate.update("DELETE FROM Beneficiary WHERE identification_number=?", beneficiary.getIdentificationNumber());
    }

    public void deleteBeneficiary(String idBeneficiary) {
        jdbcTemplate.update("DELETE FROM Beneficiary WHERE identification_number=?", idBeneficiary);
    }

    /*Modifica los datos de un beneficiario de la base de datos */
    public void updateBeneficiary(Beneficiary beneficiary) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        jdbcTemplate.update("UPDATE Beneficiary SET name=?, surnames=?, phone_number=?, email=?, address=?, user_name=?, password=?, social_worker_id=?, account=?  WHERE identification_number=?", beneficiary.getName(), beneficiary.getSurnames(), beneficiary.getPhoneNumber(), beneficiary.getEmail(), beneficiary.getAddress(), beneficiary.getUserName(), passwordEncryptor.encryptPassword(beneficiary.getPassword()), beneficiary.getSocialWorkerID(), beneficiary.getAccount(), beneficiary.getIdentificationNumber());
    }
    public void updateBeneficiaryWithoutEncryption(Beneficiary beneficiary) {
        jdbcTemplate.update("UPDATE Beneficiary SET name=?, surnames=?, phone_number=?, email=?, address=?, user_name=?, password=?, social_worker_id=?, account=?  WHERE identification_number=?", beneficiary.getName(), beneficiary.getSurnames(), beneficiary.getPhoneNumber(), beneficiary.getEmail(), beneficiary.getAddress(), beneficiary.getUserName(),beneficiary.getPassword(), beneficiary.getSocialWorkerID(), beneficiary.getAccount(), beneficiary.getIdentificationNumber());
    }

    /*Obetiene un beneficiario a partir de su ID. Devuelve nulo si no existe. */
    public Beneficiary getBeneficiary(String idBeneficiary) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Beneficiary WHERE identification_number=?",
                    new BeneficiaryRowMapper(),
                    idBeneficiary);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Obtiene una lista de todos los beneficiarios de la base de datos. Devuelve una lista vacia si no hay*/
    public List<Beneficiary> getBeneficiaries() {
        try {
            return jdbcTemplate.query("SELECT * FROM Beneficiary WHERE identification_number<>'' ORDER BY identification_number ",
                    new BeneficiaryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Beneficiary>();
        }
    }

    public List<Beneficiary> getBeneficiariesNoSocialWorker() {
        try {
            return jdbcTemplate.query("SELECT * FROM Beneficiary WHERE identification_number<>'' AND social_worker_id=''",
                    new BeneficiaryRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Beneficiary>();
        }
    }

    public List<Beneficiary> getBeneficiariesBySocialWorker(String socialworker) {
        try {
            return jdbcTemplate.query("SELECT * FROM Beneficiary WHERE identification_number<>'' and social_worker_id=?",
                    new BeneficiaryRowMapper(), socialworker);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Beneficiary>();
        }
    }

    public Beneficiary getBeneficiaryByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Beneficiary WHERE identification_number<>'' and user_name=?",
                    new BeneficiaryRowMapper(), username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Listar todas las listas de un mismo beneficiario*/
    public List<Request> listRequests(String identificationNumber) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE beneficiary_id=?", new RequestRowMapper(),
                    identificationNumber);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    public List<Request> listActiveRequests(String identificationNumber) {
        try {
            return jdbcTemplate.query("SELECT * FROM Request WHERE beneficiary_id=? AND start_date<=? AND final_date>=? AND status='APPROVED'", new RequestRowMapper(),
                    identificationNumber, LocalDate.now(), LocalDate.now());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<Request>();
        }
    }

    public String countRequests() {
        try {
            Integer numero = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM Request", Integer.class);
            Integer añadir = numero + 1;
            return "R" + añadir.toString();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public void addRequest(Request request) {
        jdbcTemplate.update("INSERT INTO Request VALUES(?, ?::STATUSTYPE, ?::SERVICETYPE, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                countRequests(), "UNSOLVED", request.getType(), request.getStart_date(),
                request.getFinal_date(), null, null, request.getComments(),
                request.getBeneficiary_id(), "", null, null);
    }

    public List<Request> activeServices(String identificationNumber) {
        return jdbcTemplate.query("SELECT * FROM Request WHERE beneficiary_id=? AND start_date<=current_date AND final_date>=current_date AND status='APPROVED'", new RequestRowMapper(), identificationNumber);
    }

    public int conseguirNumero() {
        List<Beneficiary> lista = getBeneficiaries();
        /*int numero_anterior = Integer.parseInt(lista.get(0).getIdentificationNumber().split("b")[1]);
        for (int i = 1; i < lista.size() - 1; i++) {

            int numero_actual = Integer.parseInt(lista.get(i).getIdentificationNumber().split("b")[1]);

            if (numero_actual - numero_anterior == 2) {
                return numero_actual - 1;
            }

            numero_anterior = numero_actual;
        }
*/
        return lista.size() + 1;
    }
}
