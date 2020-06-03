package seniorsathome.seniorsathomespring.dao;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Invoice;
import seniorsathome.seniorsathomespring.model.Volunteer;
import seniorsathome.seniorsathomespring.dao.VolunteerRowMapper;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class VolunteerDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*Añadir un voluntario*/
    public void addVolunteer (Volunteer v) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        jdbcTemplate.update("INSERT INTO Volunteer VALUES(?,?,?, ?,?,?,?,?,?,?::STATUSTYPE,?)",
                "V"+conseguirNumero(),v.getName(),v.getPhoneNumber(),v.getEmail(),v.getAddress(),
                v.getUserName(),passwordEncryptor.encryptPassword(v.getPassword()), LocalDate.now(),null,"UNSOLVED",v.getDescription());
    }
    public int conseguirNumero() {
        List<Volunteer> lista = getVolunteers();
        int numero_anterior = Integer.parseInt(lista.get(0).getIdNumber().split("V")[1]);
        for (int i = 1; i < lista.size(); i++) {

            int numero_actual = Integer.parseInt(lista.get(i).getIdNumber().split("V")[1]);

            if (numero_actual - numero_anterior >= 2) {
                return numero_actual - 1;
            }

            numero_anterior = numero_actual;
        }

        return lista.size() + 1;
    }

    /*Eliminar un voluntario*/
    public void deleteVolunteer(String idNumber){
        jdbcTemplate.update("DELETE FROM Volunteer WHERE idnumber=?", idNumber);
    }

    /*Actualizar voluntario con nueva contraseña*/
    public void updateVolunteer(Volunteer v) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        jdbcTemplate.update("UPDATE Volunteer SET name=?,phonenumber=?,email=?,address=?,username=?,password=?,requestdate=?,approvaldate=?,status=?::STATUSTYPE,description=? WHERE idnumber=?",
                v.getName(),v.getPhoneNumber(),v.getEmail(),v.getAddress(),
                v.getUserName(),passwordEncryptor.encryptPassword(v.getPassword()),v.getRequestDate(),v.getApprovalDate(),v.getStatus(),v.getDescription(),v.getIdNumber());
    }

    /*Actualizar voluntario con misma contraseña*/
    public void updateVolunteerWithoutEncription(Volunteer v) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        jdbcTemplate.update("UPDATE Volunteer SET name=?,phonenumber=?,email=?,address=?,username=?,password=?,requestdate=?,approvaldate=?,status=?::STATUSTYPE,description=? WHERE idnumber=?",
                v.getName(),v.getPhoneNumber(),v.getEmail(),v.getAddress(),
                v.getUserName(),v.getPassword(),v.getRequestDate(),v.getApprovalDate(),v.getStatus(),v.getDescription(),v.getIdNumber());
    }

    /*Mostrar un voluntario según su ID*/
    public  Volunteer getVolunteer (String idNumber){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE idnumber=?",
                    new VolunteerRowMapper(),
                    idNumber);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Mostrar un voluntario según su nombre de usuario*/
    public  Volunteer getVolunteerByUsername (String username){
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Volunteer WHERE userName =?",
                    new VolunteerRowMapper(),
                    username);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /*Mostrar la lista de voluntarios (todos los estados)*/
    public List<Volunteer> getVolunteers(){
        try{
            return jdbcTemplate.query("SELECT * FROM Volunteer WHERE idNumber<>'' ORDER BY (regexp_split_to_array(idNumber, E'V'))[2]::INTEGER",
                    new VolunteerRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }
    }

    /*Mostrar la lista de los voluntarios a la espera de ser procesados*/
    public List<Volunteer> getVolunteersUnsolved(){
        try{
            return jdbcTemplate.query("SELECT * FROM Volunteer WHERE idNumber<>'' and status='UNSOLVED'",
                    new VolunteerRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Volunteer>();
        }
    }
}
