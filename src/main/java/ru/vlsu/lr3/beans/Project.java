package ru.vlsu.lr3.beans;

import java.util.List;

public class Project {
    private int id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String priority;
    private String status;
    private List<Integer> responsiblePersons; // Ответственные лица (менеджеры проекта)
    private List<User> participants; // Участники проекта

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getResponsiblePersons() {
        return responsiblePersons;
    }

    public void setResponsiblePersons(List<Integer> responsiblePersons) {
        this.responsiblePersons = responsiblePersons;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}
