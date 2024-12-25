package ru.vlsu.lr3.service;

import ru.vlsu.lr3.beans.Project;
import ru.vlsu.lr3.beans.User;
import ru.vlsu.lr3.dao.ProjectDAO;
import ru.vlsu.lr3.dao.UserDAO;

import java.util.List;

public class ProjectService {

    private final ProjectDAO projectDAO;
    private final UserDAO userDAO;

    public ProjectService(ProjectDAO projectDAO, UserDAO userDAO) {
        this.projectDAO = projectDAO;
        this.userDAO = userDAO;
    }

    public void addProject(Project project, List<Integer> responsiblePersonIds, List<Integer> participantIds) {
        projectDAO.addProject(project, responsiblePersonIds, participantIds);
    }

    public Project getProject(int id) {
        return projectDAO.getProject(id);
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    public void deleteProject(int id) {
        projectDAO.deleteProject(id);
    }

    public void updateProject(Project project, List<Integer> responsiblePersonIds, List<Integer> participantIds) {
        projectDAO.updateProject(project, responsiblePersonIds, participantIds);
    }

    public List<User> getParticipants(int projectId) {
        return projectDAO.getParticipants(projectId);
    }

    public String getUserName(int userId) {
        return userDAO.getUserName(userId);
    }

    public void updateParticipants(int projectId, List<Integer> participantIds) {
        projectDAO.updateParticipants(projectId, participantIds);
    }
}
