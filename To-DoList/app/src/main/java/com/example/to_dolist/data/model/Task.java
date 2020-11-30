package com.example.to_dolist.data.model;

import com.example.to_dolist.base.BaseModel;

public class Task extends BaseModel {
    private String id;
    private String title;
    private String description;
    private String userId;

    public Task(String id, String title, String description, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
