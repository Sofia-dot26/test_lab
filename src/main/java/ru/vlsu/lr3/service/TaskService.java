package ru.vlsu.lr3.service;

import java.util.List;
import ru.vlsu.lr3.beans.Task;
import ru.vlsu.lr3.dao.TaskDAO;

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

    public void updateTask(Task task) {
        taskDAO.updateTask(task);
    }

    public void deleteTask(int id) {
        taskDAO.deleteTask(id);
    }

    public List<Task> getTasksByProjectId(int projectId) {
        return taskDAO.getTasksByProjectId(projectId);
    }
}
