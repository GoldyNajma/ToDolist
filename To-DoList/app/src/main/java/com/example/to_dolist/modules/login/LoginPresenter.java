package com.example.to_dolist.modules.login;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performLogin(final String email, final String password) {
        //proses login
        //if login success call redirect to tasks
        view.redirectToTasks();
    }
}