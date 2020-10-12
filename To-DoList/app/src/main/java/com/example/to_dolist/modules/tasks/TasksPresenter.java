package com.example.to_dolist.modules.tasks;

import android.os.Bundle;

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
    public void moveToAddTaskPage() {
        view.redirectToAddTask();
    }

//    @Override
//    public void initializeProfile(Bundle profileInfo) {
//        view.showProfileInfo(profileInfo);
//    }
}