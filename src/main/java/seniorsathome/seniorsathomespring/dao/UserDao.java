package seniorsathome.seniorsathomespring.dao;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import seniorsathome.seniorsathomespring.model.User;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User loadUserByUsername(String username, String password) {
        List<User> users = new ArrayList<User>();
        users.addAll(jdbcTemplate.query("SELECT user_name, password FROM Beneficiary WHERE user_name=?",
                new UserRowMapper(), username));
        users.addAll(jdbcTemplate.query("SELECT user_name, password FROM SocialWorker WHERE user_name=?",
                new UserRowMapper(), username));
        users.addAll(jdbcTemplate.query("SELECT userName, password FROM Volunteer WHERE userName=?",
                new UserRowMapper(), username));
        users.addAll(jdbcTemplate.query("SELECT userName, password FROM Company WHERE userName=?",
                new UserRowMapper(), username));
        users.addAll(jdbcTemplate.query("SELECT userName, password FROM Committee WHERE userName=?",
                new UserRowMapper(), username));

        User user = null;
        if (users.size() > 0)
            user = users.get(0);
        if (user == null)
            return null;

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            return user;
        }
        else {
            return null;
        }
    }

    public List<User> listAllUsers() {
        try{
            List<User> total = new ArrayList<User>();
             total.addAll(jdbcTemplate.query("SELECT user_name, password FROM Beneficiary WHERE user_name<>''",
                    new UserRowMapper()));
            total.addAll(jdbcTemplate.query("SELECT user_name, password FROM SocialWorker WHERE user_name<>''",
                    new UserRowMapper()));
            total.addAll(jdbcTemplate.query("SELECT userName, password FROM Volunteer WHERE userName<>''",
                    new UserRowMapper()));
            total.addAll(jdbcTemplate.query("SELECT userName, password FROM Company WHERE userName<>''",
                    new UserRowMapper()));
            total.addAll(jdbcTemplate.query("SELECT userName, password FROM Committee",
                    new UserRowMapper()));
            return total;
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<User>();
        }
    }
}
