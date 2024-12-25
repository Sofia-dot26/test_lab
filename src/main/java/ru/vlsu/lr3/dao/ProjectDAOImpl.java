package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.Project;
import ru.vlsu.lr3.beans.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class ProjectDAOImpl implements ProjectDAO {

    private final DataSource dataSource;

    public ProjectDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addProject(Project project, List<Integer> responsiblePersonIds, List<Integer> participantIds) {
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addResponsiblePersons(project.getId(), responsiblePersonIds);
        addParticipants(project.getId(), participantIds);
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
                    project.setResponsiblePersons(getResponsiblePersons(id));
                    project.setParticipants(getParticipants(id));
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
                project.setResponsiblePersons(getResponsiblePersons(project.getId()));
                project.setParticipants(getParticipants(project.getId()));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public void updateProject(Project project, List<Integer> responsiblePersonIds, List<Integer> participantIds) {
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

        updateResponsiblePersons(project.getId(), responsiblePersonIds);
        updateParticipants(project.getId(), participantIds);
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

    @Override
    public List<User> getParticipants(int projectId) {
        List<User> participants = new ArrayList<>();
        String sql = "SELECT u.* FROM project_participants pp JOIN users u ON pp.user_id = u.id WHERE pp.project_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setRole(resultSet.getString("role"));
                    user.setStatus(resultSet.getString("status"));
                    participants.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    private void addResponsiblePersons(int projectId, List<Integer> responsiblePersonIds) {
        String sql = "INSERT INTO project_responsibles (project_id, user_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Integer userId : responsiblePersonIds) {
                statement.setInt(1, projectId);
                statement.setInt(2, userId);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addParticipants(int projectId, List<Integer> participantIds) {
        String sql = "INSERT INTO project_participants (project_id, user_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (Integer userId : participantIds) {
                statement.setInt(1, projectId);
                statement.setInt(2, userId);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> getResponsiblePersons(int projectId) {
        List<Integer> responsiblePersonIds = new ArrayList<>();
        String sql = "SELECT user_id FROM project_responsibles WHERE project_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, projectId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    responsiblePersonIds.add(resultSet.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return responsiblePersonIds;
    }

    private void updateResponsiblePersons(int projectId, List<Integer> responsiblePersonIds) {
        String deleteSql = "DELETE FROM project_responsibles WHERE project_id = ?";
        String insertSql = "INSERT INTO project_responsibles (project_id, user_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            deleteStatement.setInt(1, projectId);
            deleteStatement.executeUpdate();
            for (Integer userId : responsiblePersonIds) {
                insertStatement.setInt(1, projectId);
                insertStatement.setInt(2, userId);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateParticipants(int projectId, List<Integer> participantIds) {
        String deleteSql = "DELETE FROM project_participants WHERE project_id = ?";
        String insertSql = "INSERT INTO project_participants (project_id, user_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
             PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
            deleteStatement.setInt(1, projectId);
            deleteStatement.executeUpdate();
            for (Integer userId : participantIds) {
                insertStatement.setInt(1, projectId);
                insertStatement.setInt(2, userId);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
