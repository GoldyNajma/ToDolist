package com.example.to_dolist.modules.edittask;

import com.example.to_dolist.api_responses.DeleteTaskResponse;
import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.callback.RequestCallback;
import com.example.to_dolist.data.model.Task;

public interface EditTaskContract {
    interface View extends BaseView<Presenter> {
        void redirectToHome();
        void redirectToLogin();
        void setSaveButtonEnabled(boolean isEnabled);
        void setCancelButtonEnabled(boolean isEnabled);
        void setDeleteButtonEnabled(boolean isEnabled);
        void setCompletedCheckBoxEnabled(boolean isEnabled);
        void startLoading();
        void endLoading();
        void setCurrentTaskId(int currentTaskId);
        void showTask(Task task);
    }

    interface Presenter extends BasePresenter {
        void loadTask(int id);
        boolean validateFields(String title, String description, String imagePath,
                               boolean completed);
        void saveEdit(int id, String title, String description, String imagePath,
                      boolean completed);
        void deleteTask(int id, boolean permanent);
        void restoreTask(int id);
    }

    interface Interactor {
        void requestShowTask(RequestCallback<TaskResponse> requestCallback, int id);
        void requestUpdateTask(RequestCallback<TaskResponse> requestCallback, int id, String title,
                               String description, String imagePath, boolean completed);
        void requestDeleteTask(RequestCallback<DeleteTaskResponse> requestCallback, int id,
                               boolean permanent);
        void requestRestoreTask(RequestCallback<TaskResponse> requestCallback, int id);
        String getToken();
    }
}
