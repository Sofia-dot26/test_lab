package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.Project;
import ru.vlsu.lr3.beans.User;

import java.util.List;

public interface ProjectDAO {
    void addProject(Project project, List<Integer> responsiblePersonIds, List<Integer> participantIds);
    Project getProject(int id);
    List<Project> getAllProjects();
    void updateProject(Project project, List<Integer> responsiblePersonIds, List<Integer> participantIds);
    void deleteProject(int id);
    List<User> getParticipants(int projectId);
    void updateParticipants(int projectId, List<Integer> participantIds);
}
