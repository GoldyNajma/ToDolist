package com.example.to_dolist.modules.login;

import android.widget.Toast;

import com.example.to_dolist.data.dataaccessobject.UserDataAccessObject;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.SessionRepository;

public class LoginPresenter implements LoginContract.Presenter {
    private final LoginContract.View view;
    private final SessionRepository<User> sessionRepository;
    private final UserDataAccessObject userDAO;

    public LoginPresenter(LoginContract.View view, SessionRepository<User> sessionRepository) {
        this.view = view;
        this.sessionRepository = sessionRepository;
        this.userDAO = new UserDataAccessObject();
    }

    @Override
    public void start() {
        if (sessionRepository.getSessionData() != null) {
            view.redirectToTasks();
        }
    }

    @Override
    public void performLogin(final String email, final String password) {
        //proses login
        User loggedUser = userDAO.validateUser(email, password);

        //if login success
        if (loggedUser != null) {
            sessionRepository.setSessionData(loggedUser);
            view.redirectToTasks();
        } else {
            view.showMessage("Email or password invalid");
        }
    }
}