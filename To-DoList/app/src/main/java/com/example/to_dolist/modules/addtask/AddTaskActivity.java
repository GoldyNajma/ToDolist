package com.example.to_dolist.modules.addtask;

import android.os.Bundle;
import android.view.View;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragmentHolderActivity;

public class AddTaskActivity extends BaseFragmentHolderActivity {
    AddTaskFragment addTaskFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);
        setTitle(getString(R.string.task_title_add));

        addTaskFragment = new AddTaskFragment();
        setCurrentFragment(addTaskFragment, false);
    }
}
