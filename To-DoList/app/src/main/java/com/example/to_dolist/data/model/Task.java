package com.example.to_dolist.data.model;

import com.example.to_dolist.base.BaseModel;

public class Task extends BaseModel {
    private int id;
    private String title;
    private String description;
    private String image_path;
    private boolean completed;
    private String created_at;
    private String updated_at;
    private String deleted_at;

    public Task(int id, String title, String description, String image_path, boolean completed,
                String created_at, String updated_at, String deleted_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.completed = completed;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public Task(String title, String description, String image_path, boolean completed) {
        this.title = title;
        this.description = description;
        this.image_path = image_path;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_path() {
        return image_path;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }
}
