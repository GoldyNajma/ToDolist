package com.example.to_dolist.modules.tasks;

import android.os.Bundle;
import android.view.View;

import com.example.to_dolist.base.BaseFragmentHolderActivity;

public class TasksActivity extends BaseFragmentHolderActivity {
    TasksFragment tasksFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        Bundle user = getIntent().getExtras();

        initializeView();

        btBack.setVisibility(View.VISIBLE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);

        tasksFragment = new TasksFragment();
        setCurrentFragment(tasksFragment, false);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtBackOnClick();
            }
        });
    }

    public void setBtBackOnClick() {
        TasksContract.Presenter tasksPresenter = tasksFragment.getPresenter();

        tasksPresenter.performLogout();
    }
}