package com.epam.brest.courses.dao;

/**
 * Created by khrutski on 22.10.14.
 */

import com.epam.brest.courses.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-dao-test.xml"})
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getUsers(){

        List<User> users = userDao.getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void addUser(){

        List<User> users = userDao.getUsers();
        int sizeBefore = users.size();
        User user = new User();
        user.setUserId(3L);
        user.setLogin("userLogin3");
        user.setUserName("userName3");
        userDao.addUser(user);

        users = userDao.getUsers();
        assertEquals(sizeBefore, users.size() -1);
    }

    @Test
    public void removeUser(){

        List<User> users = userDao.getUsers();
        int sizeBefore = users.size();
        userDao.removeUser(1);
        users = userDao.getUsers();

        assertEquals(sizeBefore, users.size() +1);
    }

    @Test
    public void getUserById(){

        User user = userDao.getUserById(2);
        assertNotNull(user);
    }

    @Test
    public void getUserByLogin(){

        User user = userDao.getUserByLogin("userLogin2");
        assertNotNull(user);
    }

    @Test
    public void updateUser(){

        User user = new User();


    }
}
