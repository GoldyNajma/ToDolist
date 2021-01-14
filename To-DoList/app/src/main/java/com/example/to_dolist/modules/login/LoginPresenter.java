package com.example.to_dolist.modules.login;

import android.text.TextUtils;
import android.util.Patterns;

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

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean validateFields(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            view.showMessage("All of the fields are required.");
            return false;
        }
        if (!isEmailValid(email)) {
            view.showMessage("Invalid email address.");
            return false;
        }

        return true;
    }

    private void setViewsEnabled(boolean isEnabled) {
        view.setLoginButtonEnabled(isEnabled);
        view.setRegisterButtonEnabled(isEnabled);
    }

    @Override
    public void performLogin(final String email, final String password) {
        setViewsEnabled(false);
        view.startLoading();
        interactor.requestLogin(email, password, new RequestCallback<LoginResponse>() {
            @Override
            public void requestSuccess(LoginResponse data) {
                interactor.saveToken(data.token_type + " " + data.token);
                interactor.saveUser(data.user);

                view.endLoading();
                setViewsEnabled(true);
                view.redirectToTasks();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                setViewsEnabled(true);
                view.showMessage(errorMessage);
            }
        });
    }
}