package com.example.to_dolist.modules.edittask;

import android.text.TextUtils;

import com.example.to_dolist.api_responses.DeleteTaskResponse;
import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.callback.RequestCallback;

public class EditTaskPresenter implements EditTaskContract.Presenter{
    private final EditTaskContract.View view;
    private final EditTaskContract.Interactor interactor;

    public EditTaskPresenter(EditTaskContract.View view,
                             EditTaskContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void start() {
        if (interactor.getToken() == null) {
            view.redirectToLogin();
        }
    }

    private void setViewsEnabled(boolean isEnabled) {
        view.setDeleteButtonEnabled(isEnabled);
        view.setCancelButtonEnabled(isEnabled);
        view.setSaveButtonEnabled(isEnabled);
        view.setCompletedCheckBoxEnabled(isEnabled);
    }

    @Override
    public void saveEdit(final int id,final String title, final String description,
                         String imagePath, boolean completed) {
        setViewsEnabled(false);
        view.startLoading();
        interactor.requestUpdateTask(new RequestCallback<TaskResponse>() {
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
        }, id, title, description, imagePath, completed);
    }

    @Override
    public void deleteTask(int id, boolean permanent) {
        setViewsEnabled(false);
        view.startLoading();
        interactor.requestDeleteTask(new RequestCallback<DeleteTaskResponse>() {
            @Override
            public void requestSuccess(DeleteTaskResponse data) {
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
        }, id, permanent);
    }

    @Override
    public void restoreTask(int id) {
        view.startLoading();
        interactor.requestRestoreTask(new RequestCallback<TaskResponse>() {
            @Override
            public void requestSuccess(TaskResponse data) {
                view.endLoading();
                view.showMessage(data.message);
                view.redirectToHome();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                view.showMessage(errorMessage);
            }
        }, id);
    }

    @Override
    public void loadTask(int id) {
        setViewsEnabled(false);
        view.startLoading();
        interactor.requestShowTask(new RequestCallback<TaskResponse>() {
            @Override
            public void requestSuccess(TaskResponse data) {
                view.endLoading();
                setViewsEnabled(true);
                view.showTask(data.task);
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                setViewsEnabled(true);
                view.showMessage(errorMessage);
                view.redirectToHome();
            }
        }, id);
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

}
