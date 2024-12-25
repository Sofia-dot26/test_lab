package ru.vlsu.lr3.service;

import ru.vlsu.lr3.beans.Task;
import ru.vlsu.lr3.dao.TaskDAO;

import java.util.List;

public class TaskService {

    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public void addTask(Task task) {
        taskDAO.addTask(task);
    }

    public Task getTask(int id) {
        return taskDAO.getTask(id);
    }

    public List<Task> getAllTasks() {
        return taskDAO.getAllTasks();
    }

    public void deleteTask(int id) {
        taskDAO.deleteTask(id);
    }

    public void updateTask(Task task) {
        taskDAO.updateTask(task);
    }

    public List<Task> getTasksByProjectId(int projectId) {
        return taskDAO.getTasksByProjectId(projectId);
    }
}
