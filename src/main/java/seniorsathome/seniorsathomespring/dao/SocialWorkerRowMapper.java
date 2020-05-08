package seniorsathome.seniorsathomespring.dao;

import org.springframework.jdbc.core.RowMapper;
import seniorsathome.seniorsathomespring.model.SocialWorker;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SocialWorkerRowMapper implements RowMapper<SocialWorker> {

    public SocialWorker mapRow(ResultSet rs, int rowNum) throws SQLException {

        SocialWorker sw = new SocialWorker();

        sw.setNumberid(rs.getString("numberId"));
        sw.setName(rs.getString("name"));
        sw.setSurname(rs.getString("surname"));
        sw.setEmail(rs.getString("email"));
        sw.setUser_name(rs.getString("user_name"));
        sw.setPassword(rs.getString("password"));

        return sw;
    }


}
