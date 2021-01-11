package com.example.to_dolist.data.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String image_path;
    private String created_at;
    private String updated_at;

    public User(int id, String name, String email, String password, String image_path,
                String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image_path = image_path;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(String name, String email, String password, String image_path) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image_path = image_path;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
