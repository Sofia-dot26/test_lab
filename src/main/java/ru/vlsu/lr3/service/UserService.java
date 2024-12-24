package ru.vlsu.lr3.service;

import java.sql.SQLException;
import java.util.List;

import ru.vlsu.lr3.beans.User;
import ru.vlsu.lr3.dao.UserDAO;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(User user) throws SQLException {
        userDAO.addUser(user);
    }

    public User getUser(int id) {
        return userDAO.getUser(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }

    public List<User> getUsersByIds(List<Integer> assigneeIds) {
        return userDAO.getUsersByIds(assigneeIds);
    }
}
