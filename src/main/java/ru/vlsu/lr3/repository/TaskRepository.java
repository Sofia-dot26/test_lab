package ru.vlsu.lr3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vlsu.lr3.model.Task;
import ru.vlsu.lr3.model.User;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    List<Task> findByAssigneeId(Long assigneeId);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignee")
    List<Task> findAllWithAssignees();


    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignee WHERE t.project.id = :projectId")
    List<Task> findByProjectIdWithAssignee(@Param("projectId") Long projectId);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.project WHERE t.assignee = :assignee")
    List<Task> findByAssignee(@Param("assignee") User assignee);

    @Query("SELECT t FROM Task t WHERE t.assignee.id = :userId ORDER BY " +
            "CASE WHEN t.status = 'In Progress' THEN 1 " +
            "WHEN t.status = 'Planning' THEN 2 " +
            "ELSE 3 END, " +
            "CASE WHEN t.priority = 'High' THEN 1 " +
            "WHEN t.priority = 'Medium' THEN 2 " +
            "ELSE 3 END, " +
            "t.endDate ASC")
    List<Task> findUserTasksWithPriority(@Param("userId") Long userId);


    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignee " +
            "WHERE (t.startDate <= :endDate AND t.endDate >= :startDate)")
    List<Task> findByDateRange(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignee " +
            "WHERE t.startDate >= :startDate")
    List<Task> findByStartDateGreaterThanEqual(@Param("startDate") Date startDate);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignee " +
            "WHERE t.endDate <= :endDate")
    List<Task> findByEndDateLessThanEqual(@Param("endDate") Date endDate);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignee WHERE t.status = :status")
    List<Task> findByStatus(@Param("status") String status);

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignee " +
            "WHERE t.status = :status AND " +
            "(t.startDate <= :endDate AND t.endDate >= :startDate)")
    List<Task> findByStatusAndDateRange(
            @Param("status") String status,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    
}