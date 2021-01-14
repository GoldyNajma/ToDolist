package com.example.to_dolist.modules.addtask;

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
import com.example.to_dolist.modules.home.HomeActivity;

import org.jetbrains.annotations.NotNull;

public class AddTaskFragment extends BaseFragment<AddTaskActivity, AddTaskContract.Presenter>
        implements AddTaskContract.View {
    Button btnSave;
    Button btnCancel;
    EditText etTitle;
    EditText etDescription;
    ProgressBar pbLoading;

    public AddTaskFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedStateInstance) {
        super.onCreateView(inflater, container, savedStateInstance);
        fragmentView = inflater.inflate(R.layout.fragment_task, container, false);
        btnSave = fragmentView.findViewById(R.id.task_btn_save);
        btnCancel = fragmentView.findViewById(R.id.task_btn_cancel);
        etTitle = fragmentView.findViewById(R.id.task_et_title);
        etDescription = fragmentView.findViewById(R.id.task_et_description);
        pbLoading = fragmentView.findViewById(R.id.task_pb_loading);

        fragmentView.findViewById(R.id.task_cb_completed).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.task_btn_delete).setVisibility(View.GONE);
        fragmentView.findViewById(R.id.task_btn_restore).setVisibility(View.GONE);

        mPresenter = new AddTaskPresenter(this,
                new AddTaskInteractor(
                        new TokenSessionRepository(activity)
                )
        );
        mPresenter.start();

        pbLoading.setVisibility(View.GONE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnAddOnClick();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtnCancelOnClick();
            }
        });

        return fragmentView;
    }

    public void setBtnAddOnClick() {
        String title = etTitle.getText().toString();
        String description = etDescription.getText().toString();
        String imagePath = null;
        boolean completed = false;
        boolean isValid = mPresenter.validateFields(title, description, imagePath, completed);

        if (isValid) {
            mPresenter.saveTask(title, description, imagePath, completed);
        }
    }

    public void setBtnCancelOnClick() {
        redirectToHome();
    }

    @Override
    public void setSaveButtonEnabled(boolean isEnabled) {
        btnSave.setEnabled(isEnabled);
    }

    @Override
    public void setCancelButtonEnabled(boolean isEnabled) {
        btnCancel.setEnabled(isEnabled);
    }

    @Override
    public void redirectToHome() {
        Intent intent = new Intent(activity, HomeActivity.class);

        startActivity(intent);
        activity.finish();
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);

        startActivity(intent);
        activity.finish();
    }

    public AddTaskContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
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
}