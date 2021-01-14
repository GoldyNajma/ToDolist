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
import com.example.to_dolist.modules.home.HomeActivity;

import org.jetbrains.annotations.NotNull;

public class LoginFragment extends BaseFragment<LoginActivity, LoginContract.Presenter>
        implements LoginContract.View {
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;
    ProgressBar pbLoading;

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

        etEmail = fragmentView.findViewById(R.id.login_et_email);
        etPassword = fragmentView.findViewById(R.id.login_et_password);
        btnLogin = fragmentView.findViewById(R.id.login_btn_login);
        btnRegister = fragmentView.findViewById(R.id.login_btn_register);
        pbLoading = fragmentView.findViewById(R.id.login_pb_loading);

        pbLoading.setVisibility(View.GONE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnLoginClick();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnRegisterClick();
            }
        });

        return fragmentView;
    }

    public void setBtnLoginClick() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        boolean isValidLogin = mPresenter.validateFields(email, password);

        if (isValidLogin) {
            mPresenter.performLogin(email, password);
        }
    }

    public void setBtnRegisterClick() {
        this.redirectToRegister();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void startLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void endLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setLoginButtonEnabled(boolean isEnabled) {
        btnLogin.setEnabled(isEnabled);
    }

    @Override
    public void setRegisterButtonEnabled(boolean isEnabled) {
        btnRegister.setEnabled(isEnabled);
    }

    @Override
    public void redirectToTasks() {
        startActivity(new Intent(activity, HomeActivity.class));
        activity.finish();
    }

    @Override
    public void redirectToRegister() {
        startActivity(new Intent(activity, RegisterActivity.class));
        activity.finish();
    }
}
