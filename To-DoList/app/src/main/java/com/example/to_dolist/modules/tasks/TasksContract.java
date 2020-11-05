package com.example.to_dolist.modules.tasks;

import android.os.Bundle;

import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;

import java.util.ArrayList;

public interface TasksContract {
    interface Presenter extends BasePresenter {
        void performLogout();
        User getUser();
        ArrayList<Task> getDataSet();
//        void initializeProfile();
//        void initializeTasks(ArrayList<Bundle> tasks);
//        void moveToAddTaskPage();
    }

    interface View extends BaseView<Presenter> {
        void redirectToLogin();
//        void showProfileInfo(User user);
//        void showTasks();
        void redirectToAddTask();
        void redirectToEditTask(String id);
    }
}
