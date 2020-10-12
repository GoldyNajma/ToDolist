package com.example.to_dolist.modules.addtask;

import android.os.Bundle;

public class AddTaskPresenter implements AddTaskContract.Presenter {
    private final AddTaskContract.View view;

    public AddTaskPresenter(AddTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void performAddTask() {
        //add new task to tasks
        //redirect back to tasks

        view.redirectToTasks();
    }
}