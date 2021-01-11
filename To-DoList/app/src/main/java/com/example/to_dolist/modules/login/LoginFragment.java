package com.example.to_dolist.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragment;
import com.example.to_dolist.data.source.TokenSessionRepository;
import com.example.to_dolist.data.source.UserSessionRepository;
import com.example.to_dolist.modules.register.RegisterActivity;
import com.example.to_dolist.modules.tasks.TasksActivity;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends BaseFragment<LoginActivity, LoginContract.Presenter>
        implements LoginContract.View {
    EditText etEmail;
    EditText etPassword;
    Button btLogin;
    Button btRegister;
    ProgressBar pbLogin;

    public LoginFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        mPresenter = new LoginPresenter(this,
                new LoginInteractor(
                        new UserSessionRepository(activity),
                        new TokenSessionRepository(activity)
                )
        );
        mPresenter.start();

        etEmail = fragmentView.findViewById(R.id.et_email);
        etPassword = fragmentView.findViewById(R.id.et_password);
        btLogin = fragmentView.findViewById(R.id.bt_login);
        btRegister = fragmentView.findViewById(R.id.bt_register);
        pbLogin = fragmentView.findViewById(R.id.pb_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtLoginClick();
            }
        });
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtRegisterClick();
            }
        });

        return fragmentView;
    }

    public void setBtLoginClick() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        mPresenter.performLogin(email, password);
    }

    public void setBtRegisterClick() {
        this.redirectToRegister();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void startLoading() {
        pbLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        pbLogin.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectToTasks() {
        startActivity(new Intent(activity, TasksActivity.class));
        activity.finish();
    }

    @Override
    public void redirectToRegister() {
        startActivity(new Intent(activity, RegisterActivity.class));
        activity.finish();
    }
}
