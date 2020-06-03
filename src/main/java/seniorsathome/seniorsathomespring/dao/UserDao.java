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

    /*Cargar usuario según su nombre de usuario*/
    public User loadUserByUsername(String username, String password) {
        List<User> users = new ArrayList<User>();
        users.addAll(jdbcTemplate.query("SELECT user_name, password FROM Beneficiary WHERE user_name=?",
                new UserRowMapper(), username));
        users.addAll(jdbcTemplate.query("SELECT user_name, password FROM SocialWorker WHERE user_name=?",
                new UserRowMapper(), username));
        users.addAll(jdbcTemplate.query("SELECT userName, password FROM Volunteer WHERE username=? AND status='APPROVED'",
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
            System.out.println(password);
            System.out.println(user.getPassword());
            return null;
        }
    }

    /*Mostrar el tipo de usuario de un usuario*/
    public String userType(User user) {
        String type = "";

        List<User> users = new ArrayList<User>();
        users.addAll(jdbcTemplate.query("SELECT user_name, password FROM Beneficiary WHERE user_name=?",
                new UserRowMapper(), user.getUsername()));
        if (users.size() > 0)
            return "beneficiary";
        users.addAll(jdbcTemplate.query("SELECT user_name, password FROM SocialWorker WHERE user_name=?",
                new UserRowMapper(), user.getUsername()));
        if (users.size() > 0 && type == "")
            return "socialworker";
        users.addAll(jdbcTemplate.query("SELECT userName, password FROM Volunteer WHERE userName=?",
                new UserRowMapper(), user.getUsername()));
        if (users.size() > 0 && type == "")
            return "volunteer";
        users.addAll(jdbcTemplate.query("SELECT userName, password FROM Company WHERE userName=?",
                new UserRowMapper(), user.getUsername()));
        if (users.size() > 0 && type == "")
            return "company";
        users.addAll(jdbcTemplate.query("SELECT userName, password FROM Committee WHERE userName=?",
                new UserRowMapper(), user.getUsername()));
        if (users.size() > 0 && type == "")
            if (users.get(0).getUsername().equals("casManager"))
                return "casmanager";
        if (users.get(0).getUsername().equals("casCommittee"))
            return "committee";
        if (users.get(0).getUsername().equals("casVolunteer"))
            return "casvolunteer";

        return type;
    }

    /*Lista de todos los usuarios*/
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

    public List<String> listAllUsersName() {
        try{
            List<String> total = new ArrayList<String>();
            total.addAll(jdbcTemplate.queryForList("SELECT user_name FROM Beneficiary WHERE user_name<>''",
                    String.class));
            total.addAll(jdbcTemplate.queryForList("SELECT user_name FROM SocialWorker WHERE user_name<>''",
                    String.class));
            total.addAll(jdbcTemplate.queryForList("SELECT userName FROM Volunteer WHERE userName<>''",
                    String.class));
            total.addAll(jdbcTemplate.queryForList("SELECT userName FROM Company WHERE userName<>''",
                    String.class));
            total.addAll(jdbcTemplate.queryForList("SELECT userName FROM Committee",
                    String.class));
            return total;
        }
        catch (EmptyResultDataAccessException e) {
            return new ArrayList<String>();
        }
    }
}
