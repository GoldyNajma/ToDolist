package com.example.to_dolist.modules.register;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.to_dolist.api_responses.RegisterResponse;
import com.example.to_dolist.callback.RequestCallback;

public class RegisterPresenter implements RegisterContract.Presenter {
    private final RegisterContract.View view;
    private final RegisterContract.Interactor interactor;

    public RegisterPresenter(RegisterContract.View view,
                             RegisterContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {
        if (interactor.getToken() != null) {
            view.redirectToLogin();
        }
    }

    private boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean validateFields(String name, String email,
                                  String password, String passwordConfirmation) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirmation)) {
            view.showMessage("All of the fields are required.");
            return false;
        }
        if (!isEmailValid(email)) {
            view.showMessage("Invalid email address.");
            return false;
        }
        if (password.length() < 6) {
            view.showMessage("The password must be at least 6 characters.");
            return false;
        }
        if (!password.equals(passwordConfirmation)) {
            view.showMessage("The password confirmation does not match.");
            return false;
        }

        return true;
    }

    @Override
    public void performRegister(String name, String email,
                                String password, String passwordConfirmation) {
        view.startLoading();
        interactor.requestRegister(name, email, password, passwordConfirmation,
                                   new RequestCallback<RegisterResponse>() {
            @Override
            public void requestSuccess(RegisterResponse data) {
                view.endLoading();
                view.showMessage(data.message);
                view.redirectToLogin();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                view.showMessage(errorMessage);
            }
        });
    }
}