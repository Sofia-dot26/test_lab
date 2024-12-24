package ru.vlsu.lr3.service;

import java.util.List;
import ru.vlsu.lr3.beans.Project;
import ru.vlsu.lr3.dao.ProjectDAO;

public class ProjectService {

    private final ProjectDAO projectDAO;

    public ProjectService(ProjectDAO projectDAO) {
        this.projectDAO = projectDAO;
    }

    public void addProject(Project project) {
        projectDAO.addProject(project);
    }

    public Project getProject(int id) {
        return projectDAO.getProject(id);
    }

    public List<Project> getAllProjects() {
        return projectDAO.getAllProjects();
    }

    public void updateProject(Project project) {
        projectDAO.updateProject(project);
    }

    public void deleteProject(int id) {
        projectDAO.deleteProject(id);
    }
}
