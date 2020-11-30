package com.example.to_dolist.modules.edittask;

import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.data.model.Task;

//import pens.lab.app.belajaractivity.base.BasePresenter;
//import pens.lab.app.belajaractivity.base.BaseView;
//import pens.lab.app.belajaractivity.data.model.Task;

/**
 * Created by fahrul on 13/03/19.
 */

public interface EditTaskContract {
    interface View extends BaseView<Presenter> {
        void redirectToTaskList();
        void redirectToLogin();
        void showData(Task task);
        void setId(String id);
    }

    interface Presenter extends BasePresenter {
        void saveEdit(String id, String title, String description);
        void loadData(String id);
    }
}
