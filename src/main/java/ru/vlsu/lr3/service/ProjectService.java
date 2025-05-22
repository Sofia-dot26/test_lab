package ru.vlsu.lr3.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vlsu.lr3.model.Notification;
import ru.vlsu.lr3.model.Project;
import ru.vlsu.lr3.model.User;
import ru.vlsu.lr3.repository.ProjectRepository;
import ru.vlsu.lr3.repository.UserRepository;
import ru.vlsu.lr3.service.NotificationService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, NotificationService notificationService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public void saveProject(Project project, List<Long> responsiblePersonIds, List<Long> participantIds) {
        if (responsiblePersonIds != null && !responsiblePersonIds.isEmpty()) {
            List<User> responsiblePersons = userRepository.findAllById(responsiblePersonIds);
            project.setResponsiblePersons(responsiblePersons);
        }

        if (participantIds != null && !participantIds.isEmpty()) {
            List<User> participants = userRepository.findAllById(participantIds);
            project.setParticipants(participants);
        }

        // Отправка уведомлений новым участникам
        if (participantIds != null) {
            List<User> newParticipants = userRepository.findAllById(participantIds);
            for (User participant : newParticipants) {
                String message = "You have been added to project: " + project.getName();
                notificationService.createNotification(participant, message,
                        Notification.NotificationType.PROJECT_ASSIGNMENT);
            }
        }

        projectRepository.save(project);

    }

    public Project getProject(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
//    public List<User> findByResponsiblePersons(Long userId){
//        System.console().printf("\n Start \n");
//        List<User> temUser = projectRepository.findByResponsiblePersons();
//        System.console().printf("\n Start2 \n");
//        List<User> goodUser = new ArrayList<User>();
//        for (User user:temUser){
//            if (user.getId() == userId){
//                goodUser.add(user);
//            }
//        }
//        System.console().printf("\n Start3 \n");
//        return goodUser;
//    }


    @Transactional
    public Project updateProject(Project project, List<Long> responsiblePersonIds, List<Long> participantIds) {

        Project existingProject = projectRepository.findById(project.getId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found"));

        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        existingProject.setPriority(project.getPriority());
        existingProject.setStatus(project.getStatus());

        // Обновляем ответственных лиц
        if (responsiblePersonIds != null) {
            List<User> responsiblePersons = userRepository.findByIdIn(responsiblePersonIds);
            existingProject.setResponsiblePersons(responsiblePersons);
        }

        // Обновляем участников
        if (participantIds != null) {
            List<User> participants = userRepository.findByIdIn(participantIds);
            existingProject.setParticipants(participants);
        }

        // Определяем новых участников
        if (participantIds != null) {
            List<User> currentParticipants = existingProject.getParticipants();
            List<User> newParticipants = userRepository.findAllById(participantIds);

            newParticipants.stream()
                    .filter(p -> !currentParticipants.contains(p))
                    .forEach(p -> {
                        String message = "Вы были добавлены в проект: " + existingProject.getName();
                        notificationService.createNotification(p, message,
                                Notification.NotificationType.PROJECT_ASSIGNMENT);
                    });
        }

        return projectRepository.save(existingProject);
    }

    public List<User> getParticipants(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        return project != null ? project.getParticipants() : null;
    }
}