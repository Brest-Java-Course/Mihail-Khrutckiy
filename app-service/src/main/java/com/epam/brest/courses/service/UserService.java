package com.epam.brest.courses.service;

import com.epam.brest.courses.domain.User;

import java.util.List;

/**
 * Created by khrutski on 24.10.14.
 */
public interface UserService {

    public void addUser(User user);

    public User getUserByLogin(String login);

    public User getUserById(Long id);

    public List<User> getUsers();

    public void deleteUserById(Long id);

    public void deleteUserByLogin(String login);

    public void updateUser(User user);

    //TODO log4j, del, get, update, tests...
}
