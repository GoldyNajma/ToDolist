package com.example.to_dolist.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.to_dolist.R;

import org.jetbrains.annotations.NotNull;

public abstract class BaseFragment<T extends FragmentActivity, U extends BasePresenter> extends
        Fragment {
    protected String title;
    protected T activity;
    protected View fragmentView;
    protected U mPresenter;
    protected FragmentListener fragmentListener;

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        title = getResources().getString(R.string.app_name);
        setTitle(title);

        return view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.activity = (T) context;
        this.fragmentListener = (FragmentListener) context;
    }
}
