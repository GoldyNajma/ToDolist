package com.example.to_dolist.modules.login;

import com.example.to_dolist.api_responses.LoginResponse;
import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.model.User;

public interface LoginContract {
    interface Presenter extends BasePresenter {
        boolean validateFields(String email, String password);
        void performLogin(String email, String password);
    }

    interface View extends BaseView<Presenter> {
        void setLoginButtonEnabled(boolean isEnabled);
        void setRegisterButtonEnabled(boolean isEnabled);
        void redirectToTasks();
        void redirectToRegister();
    }

    interface Interactor {
        void requestLogin(String email, String password,
                          RequestCallback<LoginResponse> responseCallback);
        void saveToken(String token);
        String getToken();
        void saveUser(User user);
    }
}
