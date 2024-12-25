package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void addUser(User user) throws SQLException;
    User getUser(int id);
    List<User> getAllUsers();
    void updateUser(User user) throws SQLException;
    void deleteUser(int id);
    List<User> getUsersByIds(List<Integer> ids);
    String getUserName(int userId);
}
