package com.example.to_dolist.modules.addtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragment;
import com.example.to_dolist.modules.login.LoginActivity;

public class AddTaskFragment extends BaseFragment<AddTaskActivity, AddTaskContract.Presenter>
        implements AddTaskContract.View {
    //    ImageView ivProfilePicture;
//    TextView tvEmail;
//    TextView tvPassword;
    Button btAdd;
    Button btCancel;
    EditText etTaskName;
    EditText etTaskDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedStateInstance) {
        super.onCreateView(inflater, container, savedStateInstance);
        fragmentView = inflater.inflate(R.layout.fragment_add_task, container, false);
        btAdd = fragmentView.findViewById(R.id.bt_add);
        btCancel = fragmentView.findViewById(R.id.bt_cancel);
        etTaskName = fragmentView.findViewById(R.id.et_task_name);
        etTaskDescription = fragmentView.findViewById(R.id.et_task_description);

        mPresenter = new AddTaskPresenter(this);
        mPresenter.start();

        setTitle("Main Page");

        return fragmentView;
    }

//    @Override
//    public void redirectToLogin() {
//        Intent intent = new Intent(activity, LoginActivity.class);
//
//        startActivity(intent);
//        activity.finish();
//    }

    @Override
    public void redirectToTasks() {
        Intent intent = new Intent(activity, AddTaskActivity.class);

        startActivity(intent);
    }

    public AddTaskContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(AddTaskContract.Presenter presenter) {
        mPresenter = presenter;
    }
}