package com.example.to_dolist.data.source;

public interface SessionRepository<T> {
    String SHARED_PREFERENCE_NAME = "SessionSharedPreferences";

    T initialize(T sessionData);
    T getSessionData();
    void setSessionData(T sessionData);
    void destroy();
    void update(T sessionData);
}
