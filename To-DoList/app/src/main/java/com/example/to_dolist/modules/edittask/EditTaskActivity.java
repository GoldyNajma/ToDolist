package com.example.to_dolist.modules.edittask;

import android.view.View;

import com.example.to_dolist.base.BaseFragmentHolderActivity;

//import pens.lab.app.belajaractivity.base.BaseFragmentHolderActivity;


public class EditTaskActivity extends BaseFragmentHolderActivity {
    EditTaskFragment editTaskFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
//        ivIcon.setImageResource(R.drawable.....);
        ivIcon.setVisibility(View.VISIBLE);

        editTaskFragment = new EditTaskFragment();
        String id = getIntent().getExtras().getString("TaskId");
        editTaskFragment.setId(id);
        setCurrentFragment(editTaskFragment, false);

    }




}
