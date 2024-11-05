package ru.vlsu.lr3.dao;

import ru.vlsu.lr3.beans.Task;

import java.util.List;

public interface TaskDAO {
    void addTask(Task task);
    Task getTask(int id);
    List<Task> getAllTasks();
    void updateTask(Task task);
    void deleteTask(int id);
}