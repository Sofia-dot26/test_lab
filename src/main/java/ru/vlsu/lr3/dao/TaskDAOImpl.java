package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class TaskDAOImpl implements TaskDAO {
    private final DataSource dataSource;

    public TaskDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (name, description, due_date, status) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getDesc());
            statement.setDate(3, new java.sql.Date(task.getDue_date().getTime()));
            statement.setString(4, task.getStatus());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                    System.out.println("Inserted Task ID: " + task.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task getTask(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Task task = new Task();
                    task.setId(resultSet.getInt("id"));
                    task.setName(resultSet.getString("name"));
                    task.setDesc(resultSet.getString("description"));
                    task.setDue_date(resultSet.getDate("due_date"));
                    task.setStatus(resultSet.getString("status"));
                    return task;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDesc(resultSet.getString("description"));
                task.setDue_date(resultSet.getDate("due_date"));
                task.setStatus(resultSet.getString("status"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET name = ?, description = ?, due_date = ?, status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getDesc());
            statement.setDate(3, new java.sql.Date(task.getDue_date().getTime()));
            statement.setString(4, task.getStatus());
            statement.setInt(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
