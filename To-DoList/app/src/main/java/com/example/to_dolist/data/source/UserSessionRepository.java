package com.example.to_dolist.data.source;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.to_dolist.data.model.User;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

public class UserSessionRepository implements SessionRepository<User> {
    private static final String SESSION_USER = "SessionUser";
    private final SharedPreferences sharedPreferences;

    public UserSessionRepository(@NotNull Context context) {
        sharedPreferences
                = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public User initialize(User sessionData) {
        setSessionData(sessionData);

        return getSessionData();
    }

    @Override
    public User getSessionData() {
        String sessionData = sharedPreferences.getString(SESSION_USER, null);

        return sessionData == null ? null : new Gson().fromJson(sessionData, User.class);
   }

    @Override
    public void setSessionData(User sessionData) {
        sharedPreferences.edit()
                .putString(SESSION_USER, new Gson().toJson(sessionData))
                .apply();
    }

    @Override
    public void destroy() {
        sharedPreferences.edit()
                .remove(SESSION_USER)
                .apply();
    }

    @Override
    public void update(User sessionData) {
        destroy();
        setSessionData(sessionData);
    }
}
