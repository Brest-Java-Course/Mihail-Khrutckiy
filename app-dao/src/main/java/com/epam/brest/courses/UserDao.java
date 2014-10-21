package com.epam.brest.courses;
import com.epam.brest.courses.domain.User;
import java.util.List;

public interface UserDao {

    public void addUser(User user);

    public List<User> getUsers();

    public void removeUser(Long user);

}
