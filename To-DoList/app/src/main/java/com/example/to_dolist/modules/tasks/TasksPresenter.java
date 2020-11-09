package com.example.to_dolist.modules.tasks;

//import android.os.Bundle;

import com.example.to_dolist.data.source.Database;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;

import java.util.ArrayList;

public class TasksPresenter implements TasksContract.Presenter {
    private final TasksContract.View view;

    public TasksPresenter(TasksContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performLogout() {
        view.redirectToLogin();
    }

    @Override
    public User getUser() {
        //get User from DB
        return new User("1", "najma.goldy354@gmail.com", "password");
    }

    @Override
    public ArrayList<Task> getDataSet() {
        //get Data from DB
        ArrayList<Task> data = Database.getInstance().getTasks();
//        ArrayList<Task> data = new ArrayList<>();
//        data.add(new Task("1","Task 1", "Kerjakan task satu"));
//        data.add(new Task("2", "Task 2", "Kerjakan task dua"));
//        data.add(new Task("3", "Task 3", "Kerjakan task tiga"));
//        data.add(new Task("4", "Task 4", "Kerjakan task empat"));

        return data;
    }

//    @Override
//    public void initializeProfile() {
//        view.showProfileInfo();
//    }

//    @Override
//    public void moveToAddTaskPage() {
//        view.redirectToAddTask();
//    }

//    @Override
//    public void initializeProfile(Bundle profileInfo) {
//        view.showProfileInfo(profileInfo);
//    }
}