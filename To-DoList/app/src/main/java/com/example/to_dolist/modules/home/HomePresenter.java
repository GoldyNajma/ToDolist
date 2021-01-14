package com.example.to_dolist.modules.home;

import com.example.to_dolist.api_responses.IndexTasksResponse;
import com.example.to_dolist.api_responses.LogoutResponse;
import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.callback.RequestCallback;

public class HomePresenter implements HomeContract.Presenter {
    private final HomeContract.View view;
    private final HomeContract.Interactor interactor;

    public HomePresenter(HomeContract.View view, HomeContract.Interactor interactor) {
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
    public void performLogout() {
        view.startLoading();
        interactor.requestLogout(new RequestCallback<LogoutResponse>() {
            @Override
            public void requestSuccess(LogoutResponse data) {
                view.endLoading();
                view.showMessage(data.message);
                interactor.deleteSession();
                view.redirectToLogin();
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                view.showMessage(errorMessage);
            }
        });
    }

    @Override
    public void loadTasks(String option) {
        view.startLoading();
        interactor.requestIndexTasks(new RequestCallback<IndexTasksResponse>() {
            @Override
            public void requestSuccess(IndexTasksResponse data) {
                view.endLoading();
                view.showUserTasks(data.tasks);
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                view.showMessage(errorMessage);
            }
        }, option);
    }

    @Override
    public void setCompletionStatus(int taskId, boolean completionStatus) {
        view.startLoading();
        interactor.requestUpdateTaskCompletion(new RequestCallback<TaskResponse>() {
            @Override
            public void requestSuccess(TaskResponse data) {
                view.endLoading();
                view.showMessage(data.message);
            }

            @Override
            public void requestFailed(String errorMessage) {
                view.endLoading();
                view.showMessage(errorMessage);
            }
        }, taskId, completionStatus);
    }

    @Override
    public void initializeUser() {
        view.showUser(interactor.getUser());
    }
}