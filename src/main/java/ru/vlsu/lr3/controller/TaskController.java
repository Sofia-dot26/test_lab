package ru.vlsu.lr3.controller;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.model.Project;
import ru.vlsu.lr3.model.Task;
import ru.vlsu.lr3.service.ProjectService;
import ru.vlsu.lr3.service.TaskService;
import ru.vlsu.lr3.service.UserService;


import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final UserService userService;

    public TaskController(TaskService taskService, ProjectService projectService, UserService userService) {
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
/*    @PreAuthorize("hasRole('MANAGER')")*/
    public String listTasks(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                            @RequestParam(required = false) String status,
                            Model model) {
        List<Task> tasks = taskService.findTasksByFilters(startDate, endDate, status);
        List<String> allStatuses = taskService.getAllTaskStatuses();

        model.addAttribute("tasks", tasks);
        model.addAttribute("allStatuses", allStatuses);
        model.addAttribute("selectedStatus", status);
        return "tasks/task";
    }

    @GetMapping("/add")
/*    @PreAuthorize("hasRole('MANAGER')")*/
    public String addTaskForm(Model model,@RequestParam("projectId") Long projectId) {

        Project project = projectService.getProject(projectId);
//        Task task = new Task();
//        task.setProject(project);
//
//        model.addAttribute("task",task);
        model.addAttribute("project", project);


        return "tasks/taskForm";
    }

    @PostMapping("/add")
/*    @PreAuthorize("hasRole('MANAGER')")*/
    public String addTask(@RequestParam String name,
                          @RequestParam String description,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                          @RequestParam String priority,
                          @RequestParam String status,
                          @RequestParam Long projectId,
                          @RequestParam Long assigneeId
                          ) {

        Task task = new Task();
        task.setName(name);
        task.setDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        task.setPriority(priority);
        task.setStatus(status);


        System.console().printf("\nasdsda %s \n",name);
        taskService.saveTask(task, projectId, assigneeId);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
/*    @PreAuthorize("hasRole('MANAGER')")*/
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
/*    @PreAuthorize("hasRole('MANAGER')")*/
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTask(id);
        model.addAttribute("task", task);
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("users", userService.getAllUsers());
        return "tasks/editTask";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public String editTask(@ModelAttribute Task task,
                           @PathVariable Long id,
                           @RequestParam("projectId") Long projectId,
                           @RequestParam("assigneeId") Long assigneeId,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        task.setId(id);
        task.setStartDate(startDate);
        task.setEndDate(endDate);
        taskService.updateTask(task, projectId, assigneeId);
        return "redirect:/tasks";
    }



}
