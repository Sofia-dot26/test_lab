package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.beans.Project;
import ru.vlsu.lr3.beans.User;
import ru.vlsu.lr3.service.ProjectService;
import ru.vlsu.lr3.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects";
    }

    @GetMapping("/add")
    public String addProjectForm(Model model) {
        model.addAttribute("project", new Project());
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "projectForm";
    }

    @PostMapping("/add")
    public String addProject(@DateTimeFormat(pattern = "yyyy-MM-dd") @ModelAttribute Project project,
                             @RequestParam("responsiblePerson") Integer responsiblePersonId,
                             @RequestParam("participants") List<Integer> participantIds) {
        List<Integer> responsiblePersonIds = List.of(responsiblePersonId);
        projectService.addProject(project, responsiblePersonIds, participantIds);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }

    @GetMapping("/edit/{id}")
    public String editProjectForm(@PathVariable int id, Model model) {
        Project project = projectService.getProject(id);
        model.addAttribute("project", project);
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "editProject";
    }

    @PostMapping("/edit/{id}")
    public String editProject(@DateTimeFormat(pattern = "yyyy-MM-dd") @ModelAttribute Project project,
                              @PathVariable int id,
                              @RequestParam("responsiblePerson") Integer responsiblePersonId,
                              @RequestParam("participants") List<Integer> participantIds) {
        List<Integer> responsiblePersonIds = List.of(responsiblePersonId);
        project.setId(id);
        projectService.updateProject(project, responsiblePersonIds, participantIds);
        return "redirect:/projects";
    }
}
