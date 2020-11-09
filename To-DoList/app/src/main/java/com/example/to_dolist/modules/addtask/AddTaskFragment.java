package com.example.to_dolist.modules.addtask;

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

public class AddTaskFragment extends BaseFragment<AddTaskActivity, AddTaskContract.Presenter>
        implements AddTaskContract.View {
    Button btAdd;
    Button btCancel;
    EditText etTaskName;
    EditText etTaskDescription;

    public AddTaskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedStateInstance) {
        super.onCreateView(inflater, container, savedStateInstance);
        fragmentView = inflater.inflate(R.layout.fragment_task, container, false);
        btAdd = fragmentView.findViewById(R.id.bt_save);
        btCancel = fragmentView.findViewById(R.id.bt_cancel);
        etTaskName = fragmentView.findViewById(R.id.et_task_title);
        etTaskDescription = fragmentView.findViewById(R.id.et_task_description);

        mPresenter = new AddTaskPresenter(this);
        mPresenter.start();
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtAddOnClick();
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToTasks();
            }
        });

        setTitle("Main Page");

        return fragmentView;
    }

    public void setBtAddOnClick() {
        String taskTitle = etTaskName.getText().toString();
        String taskDescription = etTaskDescription.getText().toString();

        mPresenter.saveData(taskTitle, taskDescription);
    }

    @Override
    public void redirectToTasks() {
        Intent intent = new Intent(activity, TasksActivity.class);
//        String email = user.getString("email");
//        String password = user.getString("password");

//        intent.putExtra("email", email);
//        intent.putExtra("password", password);
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
}