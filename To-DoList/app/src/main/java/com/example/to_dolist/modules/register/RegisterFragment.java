package com.example.to_dolist.modules.register;

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
import com.example.to_dolist.modules.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends BaseFragment<RegisterActivity, RegisterContract.Presenter>
        implements RegisterContract.View {
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordConfirmation;
    Button btnRegister;
    Button btnLogin;
    ProgressBar pbLoading;

    public RegisterFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_register, container, false);
        mPresenter = new RegisterPresenter(this,
                new RegisterInteractor(new TokenSessionRepository(activity))
        );
        mPresenter.start();

        etName  = fragmentView.findViewById(R.id.register_et_name);
        etEmail = fragmentView.findViewById(R.id.register_et_email);
        etPassword = fragmentView.findViewById(R.id.register_et_password);
        etPasswordConfirmation = fragmentView.findViewById(R.id.register_et_password_confirmation);
        btnRegister = fragmentView.findViewById(R.id.register_btn_register);
        btnLogin = fragmentView.findViewById(R.id.register_btn_login);
        pbLoading = fragmentView.findViewById(R.id.register_pb_loading);

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

    public void setBtnRegisterClick() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String passwordConfirmation = etPasswordConfirmation.getText().toString();
        boolean isValidRegistration
                = mPresenter.validateFields(name, email, password, passwordConfirmation);

        if (isValidRegistration) {
            mPresenter.performRegister(name, email, password, passwordConfirmation);
        }
    }

    public void setBtnLoginClick() {
        this.redirectToLogin();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectToLogin() {
        startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
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
    public void setLoginButtonEnabled(boolean isEnabled) {
        btnLogin.setEnabled(isEnabled);
    }

    @Override
    public void setRegisterButtonEnabled(boolean isEnabled) {
        btnRegister.setEnabled(isEnabled);
    }
}
