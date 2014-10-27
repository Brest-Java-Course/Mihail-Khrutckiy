package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;


import java.util.List;

/**
 * Created by khrutski on 24.10.14.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {

        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(),"User login should be not specified");
        Assert.notNull(user.getUserName(),"User name should be not specified");

        User existingUser = getUserByLogin(user.getLogin());

        if (existingUser != null){

            throw new IllegalArgumentException("User is present in DB");
        }

        userDao.addUser(user);
    }

    @Override
    public User getUserByLogin(String login) {

        User user = null;
        try{
            user = userDao.getUserByLogin(login);
        }
        catch (EmptyResultDataAccessException e){
            //TODO: add logger
        }
        return user;
    }

    @Override
    public List<User> getUsers() {

        List<User> users = userDao.getUsers();
        Assert.notNull(users);
        //Assert.isNull();
        return users;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public void updateUser(Long id) {

    }
}
