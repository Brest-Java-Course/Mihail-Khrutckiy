package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.util.Assert;

/**
 * Created by khrutski on 20.10.14.
 */
public class UserDaoImpl implements UserDao {

    public static final String USERID = "userid";
    public static final String LOGIN = "login";
    public static final String NAME = "name";


    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${insert_into_user_path}')).file)}")
    public String addNewUserSql = null;// = "insert into USER (userid, login, name) values (:userid,:login,:name)";

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${update_user_path}')).file)}")
    public String updateUserSql = null;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_login_path}')).file)}")
    public String selectUserByLoginSql = null;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_all_users_path}')).file)}")
    public String selectAllUsers = null;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${delete_user_path}')).file)}")
    public String deleteUserSql = null;

    @Value("#{T(org.apache.commons.io.FileUtils).readFileToString((new org.springframework.core.io.ClassPathResource('${select_user_by_id_path}')).file)}")
    public String selectUserByIdSql = null;


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

        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(),"User login should be not specified");
        Assert.notNull(user.getUserName(),"User name should be not specified");

        Map parameters = new HashMap();

        parameters.put(USERID, user.getUserId());
        parameters.put(LOGIN, user.getLogin());
        parameters.put(NAME, user.getUserName());

        namedParameterJdbcTemplate.update(addNewUserSql, parameters);
    }

    @Override
    public List<User> getUsers() {

        LOGGER.debug("getUsers()");
        //return null;
        return jdbcTemplate.query(selectAllUsers, new UserMapper());
    }

    @Override
    public void removeUser(long userId) {

        jdbcTemplate.update(deleteUserSql, userId);
    }

    @Override
    public User getUserById(long userId){

        return jdbcTemplate.queryForObject(selectUserByIdSql,new Object[]{userId}, new UserMapper());
    }

    @Override
    public User getUserByLogin(String login){

        return jdbcTemplate.queryForObject(selectUserByLoginSql,new Object[]{login}, new UserMapper());
    }

    @Override
    public void updateUser(User user) {

        Map parameters = new HashMap<String, Objects>();

        parameters.put(USERID,user.getUserId());
        parameters.put(LOGIN, user.getLogin());
        parameters.put(NAME, user.getUserName());

        namedParameterJdbcTemplate.update(updateUserSql, parameters);
    }

    public class UserMapper implements RowMapper<User>{

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {

            User user = new User();

            user.setUserId(resultSet.getLong(USERID));
            user.setLogin(resultSet.getString(LOGIN));
            user.setUserName(resultSet.getString(NAME));

            return user;
        }
    }
}
