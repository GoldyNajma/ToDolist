package com.example.to_dolist.modules.login;

import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;

public interface LoginContract {
    interface Presenter extends BasePresenter {
        void performLogin(String email, String password);
    }

    interface View extends BaseView<Presenter> {
        void showMessage(String message);
        void redirectToTasks();
    }
}
