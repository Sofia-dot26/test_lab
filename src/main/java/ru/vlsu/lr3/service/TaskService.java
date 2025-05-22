package ru.vlsu.lr3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.lr3.model.Notification;
import ru.vlsu.lr3.model.Project;
import ru.vlsu.lr3.model.Task;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.repository.ProjectRepository;
import ru.vlsu.lr3.repository.TaskRepository;
import ru.vlsu.lr3.repository.UserRepository;
import ru.vlsu.lr3.service.NotificationService;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository,
                       UserRepository userRepository, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public Task saveTask(Task task, Long projectId, Long assigneeId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            return null;
        }

        task.setProject(project);

        if (assigneeId != null) {
            User assignee = userRepository.findById(assigneeId).orElse(null);
            task.setAssignee(assignee);
        }

        Task savedTask = taskRepository.save(task);

        // Обновляем статус проекта после сохранения задачи
        updateProjectStatus(project);

        // Отправка уведомления исполнителю
        if (assigneeId != null) {
            User assignee = userRepository.findById(assigneeId).orElse(null);
            if (assignee != null) {
                String message = "New task assigned: " + task.getName() +
                        " in project: " + project.getName();
                notificationService.createNotification(assignee, message,
                        Notification.NotificationType.TASK_ASSIGNMENT);
            }
        }

        return savedTask;
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAllWithAssignees();
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public Task updateTask(Task task, Long projectId, Long assigneeId) {
        Task existingTask = taskRepository.findById(task.getId()).orElse(null);
        if (existingTask == null) {
            return null;
        }

        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setStartDate(task.getStartDate());
        existingTask.setEndDate(task.getEndDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());

        if (projectId != null) {
            Project project = projectRepository.findById(projectId).orElse(null);
            existingTask.setProject(project);
        }

        if (assigneeId != null) {
            User assignee = userRepository.findById(assigneeId).orElse(null);
            existingTask.setAssignee(assignee);
        }

        Task updatedTask = taskRepository.save(existingTask);

        // Обновляем статус проекта после обновления задачи
        updateProjectStatus(updatedTask.getProject());

        // Проверяем, изменился ли исполнитель
        if (assigneeId != null &&
                (existingTask.getAssignee() == null ||
                        !existingTask.getAssignee().getId().equals(assigneeId))) {

            User newAssignee = userRepository.findById(assigneeId).orElse(null);
            if (newAssignee != null) {
                String message = "Вам было назначено задание: " + existingTask.getName() +
                        " в проекте: " + existingTask.getProject().getName();
                notificationService.createNotification(newAssignee, message,
                        Notification.NotificationType.TASK_ASSIGNMENT);
            }
        }

        return updatedTask;
    }

    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> getTasksByProjectIdWithAssignee(Long projectId) {
        return taskRepository.findByProjectIdWithAssignee(projectId);
    }

    public List<Task> findByAssignee(User assignee) {
        return taskRepository.findByAssignee(assignee);
    }
    public List<Task> getUserTasksSorted(User user) {
        return taskRepository.findUserTasksWithPriority(user.getId());
    }

    public Map<String, List<Task>> getTasksGroupedByStatus(User user, Date startDate, Date endDate) {
        System.out.println("Getting tasks for user: " + user.getUsername());
        List<Task> userTasks = taskRepository.findByAssignee(user);

        if (startDate != null && endDate != null) {
            userTasks = userTasks.stream()
                    .filter(task ->
                            (task.getStartDate() != null && task.getEndDate() != null) &&
                                    !task.getStartDate().after(endDate) &&
                                    !task.getEndDate().before(startDate))
                    .collect(Collectors.toList());
        } else if (startDate != null) {
            userTasks = userTasks.stream()
                    .filter(task ->
                            task.getEndDate() != null &&
                                    !task.getEndDate().before(startDate))
                    .collect(Collectors.toList());
        } else if (endDate != null) {
            userTasks = userTasks.stream()
                    .filter(task ->
                            task.getStartDate() != null &&
                                    !task.getStartDate().after(endDate))
                    .collect(Collectors.toList());
        }

        System.out.println("Found " + userTasks.size() + " tasks after filtering");

        return userTasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus));
    }

    private void updateProjectStatus(Project project) {
        List<Task> tasks = taskRepository.findByProjectId(project.getId());

        boolean allCompleted = tasks.stream().allMatch(task -> "Completed".equalsIgnoreCase(task.getStatus()));
        boolean anyInProgress = tasks.stream().anyMatch(task -> "In Progress".equalsIgnoreCase(task.getStatus()));

        if (allCompleted) {
            project.setStatus("Completed");
        } else if (anyInProgress) {
            project.setStatus("In Progress");
        } else {
            project.setStatus("Planning");
        }

        projectRepository.save(project);
    }

    @Transactional
    public void updateTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setStatus(status);
        taskRepository.save(task);
        updateProjectStatus(task.getProject());
    }

    public List<Task> findTasksByDateRange(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            return taskRepository.findByDateRange(startDate, endDate);
        } else if (startDate != null) {
            return taskRepository.findByStartDateGreaterThanEqual(startDate);
        } else if (endDate != null) {
            return taskRepository.findByEndDateLessThanEqual(endDate);
        }
        return taskRepository.findAllWithAssignees();
    }

    public List<Task> findTasksByFilters(Date startDate, Date endDate, String status) {
        if (status != null && !status.isEmpty()) {
            if (startDate != null || endDate != null) {
                return taskRepository.findByStatusAndDateRange(status, startDate, endDate);
            }
            return taskRepository.findByStatus(status);
        }
        return findTasksByDateRange(startDate, endDate);
    }

    public List<String> getAllTaskStatuses() {
        return Arrays.asList("Planning", "In Progress", "Completed");
    }




}