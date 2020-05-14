package seniorsathome.seniorsathomespring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.SocialWorker;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SocialWorkerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public SocialWorker getSocialWorkerByUserName(String username) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM SocialWorker WHERE user_name=?",
                    new SocialWorkerRowMapper(),
                    username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public SocialWorker getSocialWorker(String numberId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM SocialWorker WHERE numberId=?",
                    new SocialWorkerRowMapper(),
                    numberId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<SocialWorker> getSocialWorkers() {
        try {
            return jdbcTemplate.query("SELECT * FROM SocialWorker WHERE numberId<>''",
                    new SocialWorkerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<SocialWorker>();
        }
    }
}
