package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vlsu.lr3.beans.Project;
import ru.vlsu.lr3.service.ProjectService;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String listProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "project";
    }

    @GetMapping("/add")
    public String addProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "projectForm";
    }

    @PostMapping("/add")
    public String addProject(@DateTimeFormat(pattern = "yyyy-MM-dd") @ModelAttribute Project project) {
        projectService.addProject(project);
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
        return "editProject";
    }

    @PostMapping("/edit/{id}")
    public String editProject(@DateTimeFormat(pattern = "yyyy-MM-dd") @ModelAttribute Project project, @PathVariable int id) {
        project.setId(id);
        projectService.updateProject(project);
        return "redirect:/projects";
    }
}
