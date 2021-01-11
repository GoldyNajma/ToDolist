package com.example.to_dolist.modules.register;

import com.example.to_dolist.api_responses.RegisterResponse;
import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.callback.RequestCallback;

public interface RegisterContract {
    interface Presenter extends BasePresenter {
        boolean validateFields(String name, String email,
                               String password, String passwordConfirmation);
        void performRegister(String name, String email,
                             String password, String passwordConfirmation);
    }

    interface View extends BaseView<RegisterContract.Presenter> {
        void showMessage(String message);
        void redirectToLogin();
    }

    interface Interactor {
        void requestRegister(String name, String email,
                             String password, String passwordConfirmation,
                             RequestCallback<RegisterResponse> responseCallback);
        String getToken();
    }
}
