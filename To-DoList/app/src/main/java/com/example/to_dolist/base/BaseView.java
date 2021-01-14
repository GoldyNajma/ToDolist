package com.example.to_dolist.base;

public interface BaseView<T> {
    void setPresenter(T presenter);
    void startLoading();
    void endLoading();
    void showMessage(String message);
}
