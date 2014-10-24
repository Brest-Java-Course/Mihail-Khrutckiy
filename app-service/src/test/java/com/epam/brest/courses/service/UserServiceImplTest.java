package com.epam.brest.courses.service;

/**
 * Created by khrutski on 22.10.14.
 */

import com.epam.brest.courses.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/testServiceContextSpring.xml"})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception{
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUser(){
        userService.addUser(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEmptyUser() throws Exception{
        userService.addUser(new User());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNotNullIdUser() throws Exception{

        userService.addUser(new User(12L, "", ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserWithSameLogin() throws Exception{

        userService.addUser(new User(null, "admin", "admin"));
        userService.addUser(new User(null, "admin", "admin"));
    }

    @Test
    public void testAddUser() throws Exception{

        userService.addUser(new User(null, "admin", "admin"));

        User user = userService.getUserByLogin("admin");
        assertEquals("admin", user.getLogin());
    }

}
