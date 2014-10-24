package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;

import java.util.List;

public interface UserDao {

    public void addUser(User user);

    public List<User> getUsers();

    public void removeUser(long userId);

    public User getUserById(long userId);

    public User getUserByLogin(String login);

    public void updateUser(User user);

}
