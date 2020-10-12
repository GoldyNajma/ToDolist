package com.example.to_dolist.modules.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragment;
import com.example.to_dolist.modules.tasks.TasksActivity;

public class LoginFragment extends BaseFragment<LoginActivity, LoginContract.Presenter> implements
        LoginContract.View {
    EditText etEmail;
    EditText etPassword;
    Button btLogin;

    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_login, container, false);
        mPresenter = new LoginPresenter(this);
        mPresenter.start();

        etEmail = fragmentView.findViewById(R.id.et_email);
        etPassword = fragmentView.findViewById(R.id.et_password);
        btLogin = fragmentView.findViewById(R.id.bt_login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtLoginClick();
            }
        });

        setTitle("My Login View");

        return fragmentView;
    }

    public void setBtLoginClick() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        mPresenter.performLogin(email, password);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void redirectToTasks() {
        Intent intent = new Intent(activity, TasksActivity.class);
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
        activity.finish();
    }
}
