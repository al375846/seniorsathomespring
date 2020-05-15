package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.Rater;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RaterRowMapper implements RowMapper<Rater> {
    public Rater mapRow(ResultSet rs, int rowNum) throws SQLException {

        Rater rater = new Rater();

        rater.setRaterid(rs.getString(1));
        rater.setName(rs.getString(2));
        String service = rs.getString(3);
        if (service != "CATERING" && service != "NURSING" && service != "CLEANING")
            service = "COMPANION";
        rater.setServiceType(service);
        rater.setDate(rs.getDate(4).toLocalDate());

        return rater;
    }
}