package ru.vlsu.lr3.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String message;
    private boolean isRead;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Типы уведомлений
    public enum NotificationType {
        PROJECT_ASSIGNMENT,
        TASK_ASSIGNMENT,
        SYSTEM
    }

    // Конструкторы, геттеры и сеттеры
    public Notification() {
        this.createdAt = new Date();
        this.isRead = false;
    }

    public Notification(User user, String message, NotificationType type) {
        this();
        this.user = user;
        this.message = message;
        this.type = type;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
    public NotificationType getType() { return type; }
    public void setType(NotificationType type) { this.type = type; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}