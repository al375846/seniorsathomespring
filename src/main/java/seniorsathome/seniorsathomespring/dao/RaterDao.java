package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.Rater;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RaterDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*Lista de las valoraciones de un beneficiario*/
    public List<Rater> listRaters(String beneficiaryID){
        try {
            List<Rater> total = new ArrayList<Rater>();
            total.addAll(jdbcTemplate.query("SELECT Volunteer.idNumber, Volunteer.name, Volunteer.email, VolunteersSchedule.day FROM VolunteersSchedule FULL OUTER JOIN Volunteer ON VolunteersSchedule.volunteerId = Volunteer.idNumber WHERE VolunteersSchedule.beneficiaryId=? AND VolunteersSchedule.day<CURRENT_DATE",
                    new RaterRowMapper(), beneficiaryID));
            total.addAll(jdbcTemplate.query("SELECT Company.fiscalNumber, Company.name, Request.type, Request.final_date FROM Request FULL OUTER JOIN Contract ON Request.contract_id = Contract.numberID FULL OUTER JOIN Company ON Company.fiscalNumber = Contract.companyID WHERE Request.beneficiary_id=? AND Request.status='APPROVED' AND Request.start_date<CURRENT_DATE",
                    new RaterRowMapper(), beneficiaryID));
            return total;
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<Rater>();
        }

    }
}
