package com.epam.brest.courses.service;

import com.epam.brest.courses.dao.UserDao;
import com.epam.brest.courses.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by mentee-42 on 24.10.14.
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Long addUser(User user) {
        Assert.notNull(user);
        Assert.isNull(user.getUserId());
        Assert.notNull(user.getLogin(), "User login should be specified.");
        Assert.notNull(user.getName(), "User name should be specified.");
        User existingUser = getUserByLogin(user.getLogin());
        if (existingUser != null) {
            throw new IllegalArgumentException(user + " is present in DB");
        }
        return userDao.addUser(user);
    }


    @Override
    @Transactional
    public User getUserByLogin(String login) {
        LOGGER.debug("getUserByLogin({}) ", login);
        User user = null;
        try {
            user = userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("getUserByLogin({}) ", login);
        }
        return user;
    }

    @Override
    @Transactional
    public User getUserById(long userId) {
        LOGGER.debug("getUserById({})",userId);
        return userDao.getUserById(userId);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        LOGGER.debug("get users()");
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        LOGGER.debug("updateUser({})",user);
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(Long userId) {
        LOGGER.debug("removeUser({})",userId);
        userDao.removeUser(userId);
    }
}