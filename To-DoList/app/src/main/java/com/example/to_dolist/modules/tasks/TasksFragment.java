package com.example.to_dolist.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragment;
import com.example.to_dolist.modules.addtask.AddTaskActivity;
import com.example.to_dolist.modules.login.LoginActivity;

public class TasksFragment extends BaseFragment<TasksActivity, TasksContract.Presenter>
        implements TasksContract.View {
//    ImageView ivProfilePicture;
    TextView tvEmail;
//    TextView tvPassword;
    Button btAddTask;
    Button btDeleteTask;
    ListView lvTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedStateInstance) {
        Bundle profileInfo = getActivity().getIntent().getExtras();

        super.onCreateView(inflater, container, savedStateInstance);
        fragmentView = inflater.inflate(R.layout.fragment_tasks, container, false);
        btAddTask = fragmentView.findViewById(R.id.bt_add_task);
        btDeleteTask = fragmentView.findViewById(R.id.bt_delete_task);
        lvTasks = fragmentView.findViewById(R.id.lv_tasks);

        mPresenter = new TasksPresenter(this);
        mPresenter.start();

        btAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtAddTaskOnClick();
            }
        });

        setTitle("Main Page");

        return fragmentView;
    }

    public void setBtAddTaskOnClick() {
        mPresenter.moveToAddTaskPage();
    }

    public void showProfileInfo(Bundle profileInfo) {
        String email = profileInfo.getString("email");
//        String password = profileInfo.getString("password");

        showEmail(email);
//        showPassword(password);
    }
//
//    public void showPassword(String password) {
//        String passwordText = getResources().getString(R.string.tv_password_text) + password;
//
//        tvPassword.setText(passwordText);
//    }
//
    public void showEmail(String email) {
        String emailText = getResources().getString(R.string.tv_email_text) + email;

        tvEmail.setText(emailText);
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);

        startActivity(intent);
        activity.finish();
    }

    @Override
    public void redirectToAddTask() {
        Intent intent = new Intent(activity, AddTaskActivity.class);

        startActivity(intent);
    }

    public TasksContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = presenter;
    }
}