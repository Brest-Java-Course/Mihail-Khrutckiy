package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Created by khrutski on 20.10.14.
 */
public class UserDaoImpl implements UserDao {

    public static final String ADD_NEW_USER_SQL = "insert into USER (userid, login, name) values (:userid,:login,:name)";
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger();

    public void setDataSource(DataSource dataSource){

        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void addUser(User user) {

        //jdbcTemplate.update("insert into USER (userid, login, name) values (?,?,?)", user.getUserId(), user.getLogin(), user.getUserName());
        LOGGER.debug("addUsers({})", user);
        Map parameters = new HashMap();

        parameters.put("userid", user.getUserId());
        parameters.put("login", user.getLogin());
        parameters.put("name", user.getUserName());

        namedParameterJdbcTemplate.update(ADD_NEW_USER_SQL, parameters);
    }

    @Override
    public List<User> getUsers() {

        LOGGER.debug("getUsers()");
        //return null;
        return jdbcTemplate.query("select userId, login, name from USER", new UserMapper());
    }

    @Override
    public void removeUser(long userId) {

        jdbcTemplate.update("delete from USER where userid = ?", userId);
    }

    @Override
    public User getUserById(long userId){

        String sql = "select userid, login, name from USER where userid= ?";
        return jdbcTemplate.queryForObject(sql ,new Object[]{userId}, new UserMapper());
    }

    @Override
    public User getUserByLogin(String login){

        String sql = "select userid, login, name from USER where login= ?";
        return jdbcTemplate.queryForObject(sql ,new Object[]{login}, new UserMapper());
    }

    @Override
    public void updateUser(User user) {

        Map parameters = new HashMap<String, Objects>();

        parameters.put("userid",user.getUserId());
        parameters.put("login", user.getLogin());
        parameters.put("name", user.getUserName());

        namedParameterJdbcTemplate.update("update USER set userid= :userid, login=:login, name=:name", parameters);
    }

    public class UserMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {

            User user = new User();

            user.setUserId(resultSet.getLong("userid"));
            user.setLogin(resultSet.getString("login"));
            user.setUserName(resultSet.getString("name"));

            return user;
        }
    }
}
