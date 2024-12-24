package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.Project;

import java.util.List;

public interface ProjectDAO {
    void addProject(Project project);
    Project getProject(int id);
    List<Project> getAllProjects();
    void updateProject(Project project);
    void deleteProject(int id);
}
