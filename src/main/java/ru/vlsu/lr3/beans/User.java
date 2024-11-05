package ru.vlsu.lr3.beans;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String email;
    private Date registr_date;
    private int taskId;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegistr_date() {
        return registr_date;
    }

    public void setRegistr_date(Date registr_date) {
        this.registr_date = registr_date;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + email + " " + registr_date;
    }
}
