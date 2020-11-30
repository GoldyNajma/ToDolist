package com.example.to_dolist.modules.edittask;

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
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.source.UserSessionRepository;
import com.example.to_dolist.modules.login.LoginActivity;
import com.example.to_dolist.modules.tasks.TasksActivity;

//import pens.lab.app.belajaractivity.R;
//import pens.lab.app.belajaractivity.base.BaseFragment;
//import pens.lab.app.belajaractivity.data.model.Task;
//import pens.lab.app.belajaractivity.modul.todolist.TodoListActivity;


/**
 * Created by fahrul on 13/03/19.
 */

public class EditTaskFragment extends BaseFragment<EditTaskActivity, EditTaskContract.Presenter> implements EditTaskContract.View {

    EditText etTaskTitle;
    EditText etTaskDescription;
    Button btnSave;
    String id;


    public EditTaskFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentView = inflater.inflate(R.layout.fragment_task, container, false);
        mPresenter = new EditTaskPresenter(this, new UserSessionRepository(activity));
        mPresenter.start();

//        etTaskTitle = fragmentView.findViewById(R.id.et_TaskTitle);
//        etTaskDescription = fragmentView.findViewById(R.id.etTaskDescription);
//        btnSave = fragmentView.findViewById(R.id.btnSave);
        etTaskTitle = fragmentView.findViewById(R.id.et_task_title);
        etTaskDescription = fragmentView.findViewById(R.id.et_task_description);
        btnSave = fragmentView.findViewById(R.id.bt_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBtSaveClick();
            }
        });

        setTitle("Edit Task");
        mPresenter.loadData(this.id);

        return fragmentView;
    }

    public void setBtSaveClick(){
        String id = this.id;
        String title = etTaskTitle.getText().toString();
        String description = etTaskDescription.getText().toString();
        mPresenter.saveEdit(id, title,description);
    }

    @Override
    public void setPresenter(EditTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void redirectToTaskList() {
//        Intent intent = new Intent(activity, TodoListActivity.class);
        Intent intent = new Intent(activity, TasksActivity.class);
        startActivity(intent);
        activity.finish();
    }

    @Override
    public void redirectToLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        startActivity(intent);
        activity.finish();
    }

    @Override
    public void showData(Task task) {
        this.etTaskTitle.setText(task.getTitle());
        this.etTaskDescription.setText(task.getDescription());
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }

}
