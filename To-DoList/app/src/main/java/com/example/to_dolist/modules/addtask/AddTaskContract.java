package com.example.to_dolist.modules.addtask;

import com.example.to_dolist.api_responses.TaskResponse;
import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.callback.RequestCallback;

public interface AddTaskContract {
    interface Presenter extends BasePresenter {
        boolean validateFields(String title, String description, String imagePath,
                               boolean completed);
        void saveTask(String title, String description, String imagePath, boolean completed);
    }

    interface View extends BaseView<AddTaskContract.Presenter> {
        void setSaveButtonEnabled(boolean isEnabled);
        void setCancelButtonEnabled(boolean isEnabled);
        void redirectToHome();
        void redirectToLogin();
    }

    interface Interactor {
        void requestStoreTask(RequestCallback<TaskResponse> requestCallback, String title,
                              String description, String imagePath, boolean completed);
        String getToken();
    }
}
