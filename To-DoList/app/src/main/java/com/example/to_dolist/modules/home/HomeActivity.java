package com.example.to_dolist.modules.home;

import android.view.View;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragmentHolderActivity;

public class HomeActivity extends BaseFragmentHolderActivity {
    HomeFragment homeFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setVisibility(View.VISIBLE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);
        setTitle(getString(R.string.home_title));

        homeFragment = new HomeFragment();
        setCurrentFragment(homeFragment, false);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBtBackOnClick();
            }
        });
    }

    public void setBtBackOnClick() {
        HomeContract.Presenter tasksPresenter = homeFragment.getPresenter();

        tasksPresenter.performLogout();
    }
}