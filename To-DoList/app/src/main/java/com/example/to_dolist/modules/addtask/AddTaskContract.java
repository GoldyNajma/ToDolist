package com.example.to_dolist.modules.addtask;

import com.example.to_dolist.base.BasePresenter;
import com.example.to_dolist.base.BaseView;
import com.example.to_dolist.modules.tasks.TasksContract;

public interface AddTaskContract {
    interface Presenter extends BasePresenter {
        void performAddTask();
    }

    interface View extends BaseView<AddTaskContract.Presenter> {
        void redirectToTasks();
    }
}
