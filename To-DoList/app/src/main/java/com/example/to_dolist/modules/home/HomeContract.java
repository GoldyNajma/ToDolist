package com.example.to_dolist.modules.home;

import com.example.to_dolist.api_responses.IndexTasksResponse;
import com.example.to_dolist.api_responses.LogoutResponse;
import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.model.User;

import java.util.List;

public interface HomeContract {
    interface Presenter extends BasePresenter {
        void performLogout();
        void loadTasks(String option);
        void setCompletionStatus(int taskId, boolean completionStatus);
        void initializeUser();
    }

    interface View extends BaseView<Presenter> {
        void redirectToLogin();
        void showUser(User user);
        void showUserTasks(List<Task> tasks);
        void redirectToAddTask();
        void redirectToEditTask(int id);
    }

    interface Interactor {
        void requestIndexTasks(RequestCallback<IndexTasksResponse> requestCallback, String option);
        void requestUpdateTaskCompletion(RequestCallback<TaskResponse> requestCallback,
                                          int taskId, boolean completionStatus);
        String getToken();
        void deleteSession();
        User getUser();
        void requestLogout(RequestCallback<LogoutResponse> requestCallback);
    }
}
