package com.example.to_dolist;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class Starter extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }
}
