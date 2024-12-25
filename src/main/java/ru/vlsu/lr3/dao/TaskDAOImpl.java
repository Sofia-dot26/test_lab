package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.Task;
import ru.vlsu.lr3.beans.User;

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
        String sql = "INSERT INTO tasks (name, description, start_date, end_date, priority, status, project_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setDate(3, Date.valueOf(task.getStartDate()));
            statement.setDate(4, Date.valueOf(task.getEndDate()));
            statement.setString(5, task.getPriority());
            statement.setString(6, task.getStatus());
            statement.setInt(7, task.getProject().getId());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setId(generatedKeys.getInt(1));
                    System.out.println("Inserted Task ID: " + task.getId());
                }
            }

            // Добавление исполнителей
            addAssignees(task);
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
                    task.setDescription(resultSet.getString("description"));
                    task.setStartDate(String.valueOf(resultSet.getDate("start_date")));
                    task.setEndDate(String.valueOf(resultSet.getDate("end_date")));
                    task.setPriority(resultSet.getString("priority"));
                    task.setStatus(resultSet.getString("status"));
                    // Добавьте логику для загрузки проекта и исполнителей, если они хранятся в отдельных таблицах
                    task.setAssignees(getAssignees(id));
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
                task.setDescription(resultSet.getString("description"));
                task.setStartDate(String.valueOf(resultSet.getDate("start_date")));
                task.setEndDate(String.valueOf(resultSet.getDate("end_date")));
                task.setPriority(resultSet.getString("priority"));
                task.setStatus(resultSet.getString("status"));
                // Добавьте логику для загрузки проекта и исполнителей, если они хранятся в отдельных таблицах
                task.setAssignees(getAssignees(task.getId()));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET name = ?, description = ?, start_date = ?, end_date = ?, priority = ?, status = ?, project_id = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, task.getName());
            statement.setString(2, task.getDescription());
            statement.setDate(3, Date.valueOf(task.getStartDate()));
            statement.setDate(4, Date.valueOf(task.getEndDate()));
            statement.setString(5, task.getPriority());
            statement.setString(6, task.getStatus());
            statement.setInt(7, task.getProject().getId());
            statement.setInt(8, task.getId());
            statement.executeUpdate();

            // Обновление исполнителей
            deleteAssignees(task.getId());
            addAssignees(task);
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

            // Удаление исполнителей
            deleteAssignees(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getTasksByProjectId(int projectId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE project_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Task task = new Task();
                    task.setId(resultSet.getInt("id"));
                    task.setName(resultSet.getString("name"));
                    task.setDescription(resultSet.getString("description"));
                    task.setStartDate(String.valueOf(resultSet.getDate("start_date")));
                    task.setEndDate(String.valueOf(resultSet.getDate("end_date")));
                    task.setPriority(resultSet.getString("priority"));
                    task.setStatus(resultSet.getString("status"));
                    // Добавьте логику для загрузки проекта и исполнителей, если они хранятся в отдельных таблицах
                    task.setAssignees(getAssignees(task.getId()));
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    private void addAssignees(Task task) {
        String sql = "INSERT INTO task_assignees (task_id, user_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (User assignee : task.getAssignees()) {
                statement.setInt(1, task.getId());
                statement.setInt(2, assignee.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAssignees(int taskId) {
        String sql = "DELETE FROM task_assignees WHERE task_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<User> getAssignees(int taskId) {
        List<User> assignees = new ArrayList<>();
        String sql = "SELECT u.* FROM task_assignees ta JOIN users u ON ta.user_id = u.id WHERE ta.task_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRole(resultSet.getString("role"));
                    user.setStatus(resultSet.getString("status"));
                    assignees.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignees;
    }
}
