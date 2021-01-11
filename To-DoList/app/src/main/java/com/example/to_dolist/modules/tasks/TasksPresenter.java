package com.example.to_dolist.modules.tasks;

//import android.os.Bundle;

import com.example.to_dolist.data.dataaccessobject.DataAccessObject;
import com.example.to_dolist.data.dataaccessobject.TaskDataAccessObject;
import com.example.to_dolist.data.source.Database;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.SessionRepository;

import java.util.ArrayList;

public class TasksPresenter implements TasksContract.Presenter {
    private final TasksContract.View view;
    private final SessionRepository<User> sessionRepository;
    private final TaskDataAccessObject taskDAO;

    public TasksPresenter(TasksContract.View view, SessionRepository<User> sessionRepository) {
        this.view = view;
        this.sessionRepository = sessionRepository;
        this.taskDAO = new TaskDataAccessObject();
    }

    @Override
    public void start() {
        if (sessionRepository.getSessionData() == null) {
            view.redirectToLogin();
        }
    }

    @Override
    public void performLogout() {
        sessionRepository.destroy();
        view.redirectToLogin();
    }

    @Override
    public User getUser() {
        //get User from DB
        return sessionRepository.getSessionData();
    }

    @Override
    public ArrayList<Task> getDataSet() {
        //get Data from shared preference
        return taskDAO.getUserTasks(Integer.toString(sessionRepository.getSessionData().getId()));
    }

    @Override
    public void initializeProfile() {
        view.showProfileInfo(sessionRepository.getSessionData());
    }

//    @Override
//    public void moveToAddTaskPage() {
//        view.redirectToAddTask();
//    }

//    @Override
//    public void initializeProfile(Bundle profileInfo) {
//        view.showProfileInfo(profileInfo);
//    }
}