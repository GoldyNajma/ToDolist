package com.example.to_dolist.modules.register;

import android.view.View;

import com.example.to_dolist.base.BaseFragmentHolderActivity;

public class RegisterActivity extends BaseFragmentHolderActivity {
    RegisterFragment registerFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);
        this.setTitle("Register");

        registerFragment = new RegisterFragment();
        setCurrentFragment(registerFragment, false);
    }
}
