package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by khrutski on 20.10.14.
 */
public class UserDaoImpl implements UserDao {



    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource){

        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addUser(User user) {

        jdbcTemplate.update("insert into USER (userid, login, name) values (?,?,?)", user.getUserId(), user.getLogin(), user.getUserName());

    }

    @Override
    public List<User> getUsers() {
        //return null;
        return jdbcTemplate.query("select userId, login, name from USER", new UserMapper());
    }

    @Override
    public void removeUserById(long id) {

        return jdbcTemplate.update("delete from USER where user");
    }

    @Override
    public User getUserById(){


    }

    @Override
    public User getUserByLogin(){

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
