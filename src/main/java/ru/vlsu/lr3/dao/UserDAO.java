package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void addUser(User user) throws SQLException;
    User getUser(int id);
    List<User> getAllUser();
    void updateUser(User user);
    void deleteUser(int id);
}

