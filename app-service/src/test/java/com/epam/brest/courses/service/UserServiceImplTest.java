package com.epam.brest.courses.service;

/**
 * Created by khrutski on 22.10.14.
 */

import com.epam.brest.courses.domain.User;
import org.easymock.internal.matchers.Null;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-services-test.xml"})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@Transactional
public class UserServiceImplTest {

    public static final String ADMIN = "admin";

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
    public void testAddUserWithSameLogin() throws Exception {

        userService.addUser(new User(null, ADMIN, ADMIN));
        userService.addUser(new User(null, ADMIN, ADMIN));
    }

    @Test
    public void testAddUser() throws Exception{

        userService.addUser(new User(null, ADMIN, ADMIN));

        User user = userService.getUserByLogin(ADMIN);
        assertEquals(ADMIN, user.getLogin());
    }
}
