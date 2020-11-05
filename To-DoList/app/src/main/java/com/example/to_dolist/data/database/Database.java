package com.example.to_dolist.data.database;

import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;

import java.util.ArrayList;

public class Database {
    private ArrayList<User> users;
    private ArrayList<Task> tasks;

    public Database() {
        users = new ArrayList<>();
        tasks = new ArrayList<>();
//        tasks.add(new Task("1", "Task 1", "Mengerjakan task 1"));
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

    public void addUser(User user) {
        this.getUsers().add(user);
    }

    public void addTask(Task task) {
        this.getTasks().add(task);
    }

    public Task getTaskById(String id) {
        for (Task task : this.getTasks()) {
            if (task.getId().equalsIgnoreCase(id)) {
                return task;
            }
        }

        return null;
    }

    public void setTask(Task task) {
        int index = -1;

        for (int i = 0; i < this.getTasks().size(); i++) {
            Task taskInDatabase = this.getTasks().get(i);
            if (taskInDatabase.getId().equalsIgnoreCase(task.getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            this.getTasks().set(index, task);
        }
    }

    public static Database getInstance() {
        return DatabaseHolder.INSTANCE;
    }

    private static class DatabaseHolder {
        private static final Database INSTANCE = new Database();
    }
}
