package com.example.to_dolist.modules.addtask;

import android.os.Bundle;
import android.view.View;

import com.example.to_dolist.base.BaseFragmentHolderActivity;
import com.example.to_dolist.modules.tasks.TasksFragment;

public class AddTaskActivity extends BaseFragmentHolderActivity {
    AddTaskFragment addTaskFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        Bundle user = getIntent().getExtras();

        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);

        addTaskFragment = new AddTaskFragment();
        setCurrentFragment(addTaskFragment, false);
    }
}
