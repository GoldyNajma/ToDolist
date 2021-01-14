package com.example.to_dolist.modules.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragment;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.TokenSessionRepository;
import com.example.to_dolist.data.source.UserSessionRepository;
import com.example.to_dolist.modules.addtask.AddTaskActivity;
import com.example.to_dolist.modules.edittask.EditTaskActivity;
import com.example.to_dolist.modules.login.LoginActivity;
import com.example.to_dolist.utils.Constants;
import com.example.to_dolist.utils.TasksRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomeActivity, HomeContract.Presenter>
        implements HomeContract.View {
    FloatingActionButton fabAddTask;
    RecyclerView rvTasks;
    ProgressBar pbLoading;
    CheckBox cbShowCompleted;
    CheckBox cbShowDeletedOnly;
    ImageView ivAccount;
    TextView tvUserName;
    private RecyclerView.Adapter<TasksRecyclerViewAdapter.MyViewHolder> mAdapter;

    public HomeFragment() {}

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedStateInstance) {
        super.onCreateView(inflater, container, savedStateInstance);

        fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        fabAddTask = fragmentView.findViewById(R.id.home_fab_add_task);
        pbLoading = fragmentView.findViewById(R.id.home_pb_loading);
        rvTasks = fragmentView.findViewById(R.id.home_rv_tasks);
        cbShowCompleted = fragmentView.findViewById(R.id.home_cb_show_completed);
        cbShowDeletedOnly = fragmentView.findViewById(R.id.home_cb_show_deleted_only);
        ivAccount = fragmentView.findViewById(R.id.home_iv_account);
        tvUserName = fragmentView.findViewById(R.id.home_tv_user_name);

        pbLoading.setVisibility(View.GONE);
        rvTasks.setHasFixedSize(true);
        rvTasks.setLayoutManager(new LinearLayoutManager(activity));
        setRvTaskAdapter(new ArrayList<Task>());

        mPresenter = new HomePresenter(this, new HomeInteractor(
                new UserSessionRepository(activity),
                new TokenSessionRepository(activity)
        ));
        mPresenter.start();
        mPresenter.initializeUser();

        mPresenter.loadTasks(Constants.UNCOMPLETED_ONLY_OPTION);

        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFabAddTaskClick();
            }
        });
        cbShowCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mPresenter.loadTasks(Constants.ALL_BUT_DELETED_OPTION);
                } else {
                    mPresenter.loadTasks(Constants.UNCOMPLETED_ONLY_OPTION);
                }
            }
        });
        cbShowDeletedOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbShowCompleted.setEnabled(false);
                    mPresenter.loadTasks(Constants.DELETED_ONLY_OPTION);
                } else {
                    cbShowCompleted.setEnabled(true);
                    if (cbShowCompleted.isChecked()) {
                        mPresenter.loadTasks(Constants.ALL_BUT_DELETED_OPTION);
                    } else {
                        mPresenter.loadTasks(Constants.UNCOMPLETED_ONLY_OPTION);
                    }
                }
            }
        });

        return fragmentView;
    }

    private void setRvTaskAdapter(final List<Task> adapterData) {
        mAdapter = new TasksRecyclerViewAdapter(new TasksRecyclerViewAdapter.ContextProvider() {
            @Override
            public Context getContext() {
                return activity;
            }
        }, adapterData);
        rvTasks.setAdapter(mAdapter);

        ((TasksRecyclerViewAdapter) mAdapter).setOnItemClickListener(
                new TasksRecyclerViewAdapter.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        int id = adapterData.get(position).getId();

                        Log.d("AppDebug","clicked position : "+ position);
                        redirectToEditTask(id);
                    }
                });
    }

    public void setFabAddTaskClick() {
        redirectToAddTask();
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);

        startActivity(intent);
        activity.finish();
    }

    @Override
    public void showUser(@NotNull User user) {
        tvUserName.setText(user.getName());
        if (user.getImage_path() != null) {
            String imageUrl = Constants.BASE_URL + user.getImage_path();
            Picasso.get().load(imageUrl).into(ivAccount);
        }
    }

    @Override
    public void showUserTasks(List<Task> tasks) {
        setRvTaskAdapter(tasks);
    }

    @Override
    public void redirectToAddTask() {
        Intent intent = new Intent(activity, AddTaskActivity.class);

        startActivity(intent);
        activity.finish();
    }

    @Override
    public void redirectToEditTask(int id) {
        Intent intent = new Intent(activity, EditTaskActivity.class);

        intent.putExtra(Constants.EXTRA_TASK_ID, id);
        startActivity(intent);
        activity.finish();
    }

    public HomeContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
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