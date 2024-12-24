package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import java.time.LocalDate;

public class ProjectDAOImpl implements ProjectDAO {

    private final DataSource dataSource;

    public ProjectDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addProject(Project project) {
        String sql = "INSERT INTO projects (name, description, start_date, end_date, priority, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, Date.valueOf(project.getStartDate()));
            statement.setDate(4, Date.valueOf(project.getEndDate()));
            statement.setString(5, project.getPriority());
            statement.setString(6, project.getStatus());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getInt(1));
                    System.out.println("Inserted Project ID: " + project.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Project getProject(int id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Project project = new Project();
                    project.setId(resultSet.getInt("id"));
                    project.setName(resultSet.getString("name"));
                    project.setDescription(resultSet.getString("description"));
                    project.setStartDate(String.valueOf(resultSet.getDate("start_date")));
                    project.setEndDate(String.valueOf(resultSet.getDate("end_date")));
                    project.setPriority(resultSet.getString("priority"));
                    project.setStatus(resultSet.getString("status"));
                    // Добавьте логику для загрузки ответственных лиц и участников, если они хранятся в отдельных таблицах
                    return project;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setStartDate(String.valueOf(resultSet.getDate("start_date")));
                project.setEndDate(String.valueOf(resultSet.getDate("end_date")));
                project.setPriority(resultSet.getString("priority"));
                project.setStatus(resultSet.getString("status"));
                // Добавьте логику для загрузки ответственных лиц и участников, если они хранятся в отдельных таблицах
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public void updateProject(Project project) {
        String sql = "UPDATE projects SET name = ?, description = ?, start_date = ?, end_date = ?, priority = ?, status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, Date.valueOf(project.getStartDate()));
            statement.setDate(4, Date.valueOf(project.getEndDate()));
            statement.setString(5, project.getPriority());
            statement.setString(6, project.getStatus());
            statement.setInt(7, project.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProject(int id) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
