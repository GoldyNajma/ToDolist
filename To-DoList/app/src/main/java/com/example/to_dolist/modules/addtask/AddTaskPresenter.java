package com.example.to_dolist.modules.addtask;

import android.text.TextUtils;

import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.callback.RequestCallback;

public class AddTaskPresenter implements AddTaskContract.Presenter {
    private final AddTaskContract.View view;
    private final AddTaskContract.Interactor interactor;

    public AddTaskPresenter(AddTaskContract.View view, AddTaskContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {
        if (interactor.getToken() == null) {
            view.redirectToLogin();
        }
    }

    @Override
    public boolean validateFields(String title, String description, String imagePath,
                                  boolean completed) {
        if (TextUtils.isEmpty(title)) {
            view.showMessage("Task title is required.");
            return false;
        } else if (title.length() > 255) {
            view.showMessage("Task title should be less than or equals 255 characters.");
        }
        if (!TextUtils.isEmpty(description) && description.length() > 65535) {
            view.showMessage("Task description should be less than or equals 65535 characters.");
            return false;
        }

        return true;
    }

    private void setViewsEnabled(boolean isEnabled) {
        view.setCancelButtonEnabled(isEnabled);
        view.setSaveButtonEnabled(isEnabled);
    }

    @Override
    public void saveTask(String title, String description, String imagePath, boolean completed) {
        setViewsEnabled(false);
        view.startLoading();
        interactor.requestStoreTask(new RequestCallback<TaskResponse>() {
            @Override
            public void requestSuccess(TaskResponse data) {
                view.endLoading();
                setViewsEnabled(true);
                view.showMessage(data.message);
                view.redirectToHome();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                setViewsEnabled(true);
                view.showMessage(errorMessage);
            }
        }, title, description, imagePath, completed);
    }
}