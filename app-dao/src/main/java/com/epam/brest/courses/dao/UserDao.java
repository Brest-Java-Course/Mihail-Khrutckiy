package com.epam.brest.courses.dao;

import com.epam.brest.courses.domain.User;
import java.util.List;

public interface UserDao {

    public void addUser(User user);

    public List<User> getUsers();

    public void removeUser(Long user);

    public User getUserById();

    public User getUserByLogin();

}
