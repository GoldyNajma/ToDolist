package com.example.to_dolist.modules.addtask;

import android.view.View;

import com.example.to_dolist.base.BaseFragmentHolderActivity;
import com.example.to_dolist.modules.tasks.TasksFragment;

public class AddTaskActivity extends BaseFragmentHolderActivity {
    TasksFragment tasksFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);

        tasksFragment = new TasksFragment();
        setCurrentFragment(tasksFragment, false);
    }
}
