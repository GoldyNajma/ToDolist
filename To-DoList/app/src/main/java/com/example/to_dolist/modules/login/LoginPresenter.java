package com.example.to_dolist.modules.login;

import com.example.to_dolist.api_responses.LoginResponse;
import com.example.to_dolist.callback.RequestCallback;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final LoginContract.Interactor interactor;

    public LoginPresenter(LoginContract.View view,
                          LoginContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {
        if (interactor.getToken() != null) {
            view.redirectToTasks();
        }
    }

    @Override
    public void performLogin(final String email, final String password) {
        view.startLoading();
        interactor.requestLogin(email, password, new RequestCallback<LoginResponse>() {
            @Override
            public void requestSuccess(LoginResponse data) {
                interactor.saveToken(data.token_type + " " + data.token);
                interactor.saveUser(data.user);

                view.endLoading();
                view.showMessage("Token: " + interactor.getToken());
//                view.redirectToTasks();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                view.showMessage(errorMessage);
            }
        });
    }
}