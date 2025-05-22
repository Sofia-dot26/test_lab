package ru.vlsu.lr3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vlsu.lr3.model.Task;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.service.SecurityService;
import ru.vlsu.lr3.service.SecurityServiceImpl;
import ru.vlsu.lr3.service.TaskService;
import ru.vlsu.lr3.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final TaskService taskService;
    private final UserService userService;
    private final SecurityServiceImpl securityService;

    @Autowired
    public MainController(TaskService taskService, UserService userService,SecurityServiceImpl securityService) {
        this.taskService = taskService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                       @RequestParam(required = false) String inProgressSort,
                       @RequestParam(required = false) String planningSort,
                       @RequestParam(required = false) String completedSort,
                       @RequestParam(defaultValue = "1") int inProgressPage,
                       @RequestParam(defaultValue = "1") int planningPage,
                       @RequestParam(defaultValue = "1") int completedPage) {

        if (!securityService.isAuthenticated()){
            return "redirect:/login";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User currentUser = userService.findByUsername(username);

        // Получаем задачи с группировкой по статусу
        Map<String, List<Task>> tasksByStatus = taskService.getTasksGroupedByStatus(currentUser, startDate, endDate);

        // Применяем сортировку для каждого блока задач
        List<Task> inProgressTasks = tasksByStatus.getOrDefault("In Progress", Collections.emptyList());
        List<Task> planningTasks = tasksByStatus.getOrDefault("Planning", Collections.emptyList());
        List<Task> completedTasks = tasksByStatus.getOrDefault("Completed", Collections.emptyList());

        // Сортируем задачи
        inProgressTasks = sortTasks(inProgressTasks, inProgressSort);
        planningTasks = sortTasks(planningTasks, planningSort);
        completedTasks = sortTasks(completedTasks, completedSort);

        // Пагинация для каждого раздела
        int pageSize = 3;

        // In Progress
        int inProgressTotalItems = inProgressTasks.size();
        int inProgressTotalPages = (int) Math.ceil((double) inProgressTotalItems / pageSize);
        inProgressPage = Math.max(1, Math.min(inProgressPage, inProgressTotalPages));
        List<Task> inProgressPageTasks = paginateList(inProgressTasks, inProgressPage, pageSize);

        // Planning
        int planningTotalItems = planningTasks.size();
        int planningTotalPages = (int) Math.ceil((double) planningTotalItems / pageSize);
        planningPage = Math.max(1, Math.min(planningPage, planningTotalPages));
        List<Task> planningPageTasks = paginateList(planningTasks, planningPage, pageSize);

        // Completed
        int completedTotalItems = completedTasks.size();
        int completedTotalPages = (int) Math.ceil((double) completedTotalItems / pageSize);
        completedPage = Math.max(1, Math.min(completedPage, completedTotalPages));
        List<Task> completedPageTasks = paginateList(completedTasks, completedPage, pageSize);

        model.addAttribute("inProgressTasks", inProgressPageTasks);
        model.addAttribute("planningTasks", planningPageTasks);
        model.addAttribute("completedTasks", completedPageTasks);

        model.addAttribute("inProgressTotalPages", inProgressTotalPages);
        model.addAttribute("planningTotalPages", planningTotalPages);
        model.addAttribute("completedTotalPages", completedTotalPages);

        model.addAttribute("inProgressTotalItems", inProgressTotalItems);
        model.addAttribute("planningTotalItems", planningTotalItems);
        model.addAttribute("completedTotalItems", completedTotalItems);

        model.addAttribute("inProgressPage", inProgressPage);
        model.addAttribute("planningPage", planningPage);
        model.addAttribute("completedPage", completedPage);

        model.addAttribute("username", username);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("inProgressSort", inProgressSort);
        model.addAttribute("planningSort", planningSort);
        model.addAttribute("completedSort", completedSort);

        return "home";
    }
    private List<Task> sortTasks(List<Task> tasks, String sortOrder) {
        if (sortOrder == null) return tasks;

        return tasks.stream()
                .sorted((t1, t2) -> {
                    if (t1.getEndDate() == null && t2.getEndDate() == null) return 0;
                    if (t1.getEndDate() == null) return 1;
                    if (t2.getEndDate() == null) return -1;

                    return "desc".equals(sortOrder) ?
                            t2.getEndDate().compareTo(t1.getEndDate()) :
                            t1.getEndDate().compareTo(t2.getEndDate());
                })
                .collect(Collectors.toList());
    }

    private <T> List<T> paginateList(List<T> list, int page, int pageSize) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        int fromIndex = (page - 1) * pageSize;
        if (fromIndex >= list.size()) {
            return Collections.emptyList();
        }

        int toIndex = Math.min(fromIndex + pageSize, list.size());
        return list.subList(fromIndex, toIndex);
    }

    @PostMapping("/task/{id}/start")
    public String startTask(@PathVariable Long id) {
        taskService.updateTaskStatus(id, "In Progress");
        return "redirect:/";
    }

    @PostMapping("/task/{id}/complete")
    public String completeTask(@PathVariable Long id) {
        taskService.updateTaskStatus(id, "Completed");
        return "redirect:/";
    }
}
