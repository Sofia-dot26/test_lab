package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.beans.Project;
import ru.vlsu.lr3.beans.Task;
import ru.vlsu.lr3.beans.User;
import ru.vlsu.lr3.service.ProjectService;
import ru.vlsu.lr3.service.TaskService;
import ru.vlsu.lr3.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "task";
    }

    @GetMapping("/add")
    public String addTaskForm(Model model) {
        model.addAttribute("task", new Task());
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "taskForm";
    }

    @PostMapping("/add")
    public String addTask(@DateTimeFormat(pattern = "yyyy-MM-dd") @ModelAttribute Task task,
                          @RequestParam("projectId") int projectId,
                          @RequestParam("assigneeIds") List<Integer> assigneeIds) {
        Project project = projectService.getProject(projectId);
        task.setProject(project);
        List<User> assignees = userService.getUsersByIds(assigneeIds);
        task.setAssignees(assignees);
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable int id, Model model) {
        Task task = taskService.getTask(id);
        model.addAttribute("task", task);
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "editTask";
    }

    @PostMapping("/edit/{id}")
    public String editTask(@DateTimeFormat(pattern = "yyyy-MM-dd") @ModelAttribute Task task,
                           @PathVariable int id,
                           @RequestParam("projectId") int projectId,
                           @RequestParam("assigneeIds") List<Integer> assigneeIds) {
        Project project = projectService.getProject(projectId);
        task.setProject(project);
        List<User> assignees = userService.getUsersByIds(assigneeIds);
        task.setAssignees(assignees);
        task.setId(id);
        taskService.updateTask(task);
        return "redirect:/tasks";
    }
}
