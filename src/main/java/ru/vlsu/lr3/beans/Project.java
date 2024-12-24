package ru.vlsu.lr3.beans;

import java.util.List;

public class Project {
    private int id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private List<String> responsiblePersons; // Ответственные лица (менеджеры проекта)
    private List<String> participants; // Участники проекта
    private String priority;
    private String status;

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

    public List<String> getResponsiblePersons() {
        return responsiblePersons;
    }

    public void setResponsiblePersons(List<String> responsiblePersons) {
        this.responsiblePersons = responsiblePersons;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
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
}
