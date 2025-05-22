package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.model.Project;
import ru.vlsu.lr3.model.Task;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.service.ProjectService;
import ru.vlsu.lr3.service.TaskService;
import ru.vlsu.lr3.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;


    public ProjectController(ProjectService projectService, UserService userService,TaskService taskService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping
    /*@PreAuthorize("hasRole('MANAGER')")*/
    public String listProjects(Model model) {
        System.console().printf("\nStart\n");
        List<Project> projects = projectService.getAllProjects();

        model.addAttribute("projects", projects);
        return "projects/project";
    }

    @GetMapping("/add")
    /*@PreAuthorize("hasRole('MANAGER')")*/
    public String addProjectForm(Model model) {
        model.addAttribute("project", new Project());
        List<User> users = userService.getAllUsers();
        System.out.println("Users count: " + users.size());
        model.addAttribute("users", users);
        return "projects/projectForm";
    }
//    у меня тоже упал
    @PostMapping("/add")
    public String addProject(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam String priority,
            @RequestParam String status,
            @RequestParam("responsiblePersons") List<Long> responsiblePersonIds,
            @RequestParam(value = "participants", required = false) List<Long> participantIds) {

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(priority);
        project.setStatus(status);

        projectService.saveProject(project, responsiblePersonIds,
                participantIds != null ? participantIds : Collections.emptyList());

        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    /*@PreAuthorize("hasRole('MANAGER')")*/
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
    @GetMapping("/edit/{id}")
  /*  @PreAuthorize("hasRole('MANAGER')")*/
    public String editProjectForm(@PathVariable Long id, Model model) {
        Project project = projectService.getProject(id);
        List<User> responsiblePersons = project.getResponsiblePersons();
        List<Long> idsResponsiblePersons = new ArrayList<Long>();
        for (User user:responsiblePersons){
            idsResponsiblePersons.add(user.getId());
        }

        List<User> participants = project.getParticipants();
        List<Long> idsParticipants = new ArrayList<Long>();
        for (User user:participants){
            idsParticipants.add(user.getId());
        }
        List<User> users = userService.getAllUsers();

        model.addAttribute("project", project);
        model.addAttribute("users", users);
        model.addAttribute("idsResponsiblePersons", idsResponsiblePersons);
        model.addAttribute("idsParticipants", idsParticipants);
        model.addAttribute("participants", participants);

        return "projects/editProject";
    }


    @PostMapping("/edit/{id}")
    /*@PreAuthorize("hasRole('MANAGER')")*/
    public String editProject(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam String priority,
            @RequestParam String status,
            @RequestParam("responsiblePersons") List<Long> responsiblePersonIds,
            @RequestParam(value = "participants", required = false) List<Long> participantIds) {

        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setPriority(priority);
        project.setStatus(status);

        projectService.updateProject(project, responsiblePersonIds,
                participantIds != null ? participantIds : Collections.emptyList());

        return "redirect:/projects";
    }

    @GetMapping("/{id}/tasks")
    public String getProjectTasks(@PathVariable Long id, Model model, HttpServletRequest request) {
        Project project = projectService.getProject(id);
        List<Task> allTasks = taskService.getTasksByProjectIdWithAssignee(id);

        // Разделяем задачи по статусам
        List<Task> todoTasks = allTasks.stream()
                .filter(t -> "Planning".equalsIgnoreCase(t.getStatus()))
                .collect(Collectors.toList());

        List<Task> inProgressTasks = allTasks.stream()
                .filter(t -> "In Progress".equalsIgnoreCase(t.getStatus())
                        || "In process".equalsIgnoreCase(t.getStatus()))
                .collect(Collectors.toList());

        List<Task> doneTasks = allTasks.stream()
                .filter(t -> "Completed".equalsIgnoreCase(t.getStatus()))
                .collect(Collectors.toList());

        System.out.println("Total tasks: " + allTasks.size());
        System.out.println("Todo tasks: " + todoTasks.size());
        System.out.println("In progress tasks: " + inProgressTasks.size());
        System.out.println("Done tasks: " + doneTasks.size());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("project", project);
        model.addAttribute("todoTasks", todoTasks);
        model.addAttribute("inProgressTasks", inProgressTasks);
        model.addAttribute("doneTasks", doneTasks);
        model.addAttribute("username", username);


        return "projects/projectTasks";
    }
    @GetMapping("/gant/{id}")
    public String showGantChart(@PathVariable Long id, Model model){
        Project project = projectService.getProject(id);
        model.addAttribute("project", project);
        model.addAttribute("tasks", taskService.getTasksByProjectId(id));
        return "projects/GantChart";
    }

}
