package com.example.to_dolist.modules.edittask;

import android.view.View;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragmentHolderActivity;
import com.example.to_dolist.utils.Constants;

public class EditTaskActivity extends BaseFragmentHolderActivity {
    EditTaskFragment editTaskFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        int id = getIntent().getExtras().getInt(Constants.EXTRA_TASK_ID);

        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);
        setTitle(getString(R.string.task_title_edit));

        editTaskFragment = new EditTaskFragment();
        editTaskFragment.setCurrentTaskId(id);
        setCurrentFragment(editTaskFragment, false);
    }




}
