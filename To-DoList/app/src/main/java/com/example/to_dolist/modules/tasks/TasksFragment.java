package com.example.to_dolist.modules.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragment;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.UserSessionRepository;
import com.example.to_dolist.modules.addtask.AddTaskActivity;
import com.example.to_dolist.modules.edittask.EditTaskActivity;
import com.example.to_dolist.modules.login.LoginActivity;
import com.example.to_dolist.utils.RecyclerViewAdapterTodolist;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TasksFragment extends BaseFragment<TasksActivity, TasksContract.Presenter>
        implements TasksContract.View {
    TextView tvEmail;
    Button btAddTask;
    RecyclerView rvTasks;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public TasksFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedStateInstance) {
        super.onCreateView(inflater, container, savedStateInstance);
        fragmentView = inflater.inflate(R.layout.fragment_tasks, container, false);
        tvEmail = fragmentView.findViewById(R.id.tv_email);
        btAddTask = fragmentView.findViewById(R.id.bt_add_task);

        mPresenter = new TasksPresenter(this, new UserSessionRepository(activity));
        mPresenter.start();
        mPresenter.initializeProfile();

        rvTasks = fragmentView.findViewById(R.id.rv_tasks);
        rvTasks.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        rvTasks.setLayoutManager(mLayoutManager);

        final ArrayList<Task> data = mPresenter.getDataSet();
        mAdapter = new RecyclerViewAdapterTodolist(data);
        rvTasks.setAdapter(mAdapter);


        ((RecyclerViewAdapterTodolist) mAdapter).setOnItemClickListener(
                new RecyclerViewAdapterTodolist.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String id = data.get(position).getId();
                Log.d("AppDebug","clicked position : "+ position);
                redirectToEditTask(id);
            }
        });

        btAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToAddTask();
            }
        });

        setTitle("Main Page");

        return fragmentView;
    }

//    public void setBtAddTaskOnClick() {
////        mPresenter.moveToAddTaskPage();
//        redirectToAddTask();
//    }

//    public void showProfileInfo() {
//        String email = user.getString("email");
////        String password = user.getString("password");
//
//        showEmail(email);
////        showPassword(password);
//    }

    public void showEmail(String email) {
        String emailText = getResources().getString(R.string.tv_email_text) + email;

        tvEmail.setText(emailText);
    }

//    @Override
//    public void showTasks() {
//
//    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);

        startActivity(intent);
        activity.finish();
    }

    @Override
    public void showProfileInfo(User user) {
        tvEmail.setText(user.getEmail());
    }

    @Override
    public void redirectToAddTask() {
        Intent intent = new Intent(activity, AddTaskActivity.class);
//        String email = user.getString("email");
//        String password =user.getString("password");

//        intent.putExtra("email", email);
//        intent.putExtra("password", password);
        startActivity(intent);
        activity.finish();
    }

    public void redirectToEditTask(String id) {
        Intent intent = new Intent(activity, EditTaskActivity.class);
        intent.putExtra("TaskId", id);
        startActivity(intent);
        activity.finish();
    }

    public TasksContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }
}