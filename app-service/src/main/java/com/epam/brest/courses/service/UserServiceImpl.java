package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.Assert;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.util.List;

/**
 * Created by khrutski on 24.10.14.
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private static final Logger LOGGER = LogManager.getLogger();


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

        LOGGER.debug("User has been added :" + user);


    }

    @Override
    public User getUserByLogin(String login) {

        User user = null;
        try{
            user = userDao.getUserByLogin(login);
        }
        catch (EmptyResultDataAccessException e){

            LOGGER.debug(e.toString());
        }

        LOGGER.debug(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {

        User user = null;
        try{
            user = userDao.getUserById(id);
        }
        catch (EmptyResultDataAccessException e){

            LOGGER.debug(e.toString());
        }

        LOGGER.debug(user);
        return user;
    }

    @Override
    public List<User> getUsers() {

        List<User> users = userDao.getUsers();
        Assert.notNull(users);
        Assert.isTrue(users.size() > 0, "List's users is null");
        User [] array = users.toArray(new User[users.size()]);
        Assert.noNullElements(array, "List contains null elements");

        LOGGER.debug(users);
        return users;
    }

    @Override
    public void deleteUserById(Long id) {

        User userForDelete = getUserById(id);
        Assert.notNull(userForDelete, "User is null with id :" + id);
        boolean res = userForDelete.getLogin().equals("admin");
        Assert.isTrue(!res,"You can't to delete admin's account");

        userDao.removeUser(id);

        LOGGER.debug("User has been deleted by id:" +  userForDelete);
    }

    @Override
    public void deleteUserByLogin(String login){

        User userForDelete = getUserByLogin(login);
        Assert.isTrue(login.length() > 0, "login is empty");
        Assert.notNull(userForDelete, "User is null with login :" + login);
        boolean res = userForDelete.getLogin().equals("admin");
        Assert.isTrue(!res,"You can't to delete admin's account");

        userDao.removeUser(userForDelete.getUserId());

        LOGGER.debug("User has been deleted bu login:" +  userForDelete);
    }

    @Override
    public void updateUser(User user) {

        Assert.notNull(user);
        Assert.notNull(user.getUserId());
        Assert.notNull(user.getLogin(),"User login should be not specified");
        Assert.notNull(user.getUserName(),"User name should be not specified");

        User userForUpdate = getUserById(user.getUserId());
        Assert.notNull(userForUpdate, "User is null with login :" + user.getUserId());
        boolean res = userForUpdate.getLogin().equals("admin");
        Assert.isTrue(!res,"You can't update admin's account");

        userDao.updateUser(user);

        LOGGER.debug("User has been added" + user);
    }
}
