package com.example.to_dolist.data.source;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

public class TokenSessionRepository implements SessionRepository<String>{
    private static final String SESSION_TOKEN = "SessionToken";
    private final SharedPreferences sharedPreferences;

    public TokenSessionRepository(@NotNull Context context) {
        sharedPreferences
                = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public String initialize(String sessionData) {
        setSessionData(sessionData);

        return getSessionData();
    }

    @Override
    public String getSessionData() {
        return sharedPreferences.getString(SESSION_TOKEN, null);
    }

    @Override
    public void setSessionData(String sessionData) {
        sharedPreferences.edit()
                .putString(SESSION_TOKEN, sessionData)
                .apply();
    }

    @Override
    public void destroy() {
        sharedPreferences.edit()
                .remove(SESSION_TOKEN)
                .apply();
    }

    @Override
    public void update(String sessionData) {
        destroy();
        setSessionData(sessionData);
    }
}
