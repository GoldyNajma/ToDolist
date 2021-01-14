package com.example.to_dolist.modules.login;

import android.view.View;

import com.example.to_dolist.R;
import com.example.to_dolist.base.BaseFragmentHolderActivity;

public class LoginActivity extends BaseFragmentHolderActivity {
    LoginFragment loginFragment;
    private final int UPDATE_REQUEST = 2019;

    @Override
    protected void initializeFragment() {
        initializeView();

        btBack.setVisibility(View.GONE);
        btOptionMenu.setVisibility(View.GONE);
        ivIcon.setVisibility(View.VISIBLE);
        setTitle(getString(R.string.login_title));

        loginFragment = new LoginFragment();
        setCurrentFragment(loginFragment, false);
    }
}
