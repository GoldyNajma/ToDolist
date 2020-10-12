package com.example.to_dolist.modules.tasks;

import android.os.Bundle;

import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;

public interface TasksContract {
    interface Presenter extends BasePresenter {
        void performLogout();
//        void initializeProfile(Bundle profileInfo);
        void moveToAddTaskPage();
    }

    interface View extends BaseView<Presenter> {
        void showProfileInfo(Bundle profileInfo);
        void redirectToLogin();
        void redirectToAddTask();
    }
}
