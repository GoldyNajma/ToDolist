package com.example.to_dolist.data.source;

import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;

import java.util.ArrayList;

public class Database {
    private ArrayList<User> users;
    private ArrayList<Task> tasks;

    public Database() {
        users = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public static Database getInstance() {
        return DatabaseHolder.INSTANCE;
    }

    private static class DatabaseHolder {
        private static final Database INSTANCE = new Database();
    }
}
