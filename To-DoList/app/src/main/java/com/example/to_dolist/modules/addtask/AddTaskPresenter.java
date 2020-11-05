package com.example.to_dolist.modules.addtask;

import android.os.Bundle;

import com.example.to_dolist.data.database.Database;
import com.example.to_dolist.data.model.Task;

import java.util.ArrayList;

public class AddTaskPresenter implements AddTaskContract.Presenter {
    private final AddTaskContract.View view;

    public AddTaskPresenter(AddTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {}

    @Override
    public void saveData(final String title, final String description) {
        if (!title.equals("")) {
//            ArrayList<Task> tasks = Database.getInstance().getTasks();
//            int latestId = Integer.parseInt(tasks.get(tasks.size() - 1).getId());
//            String id = Integer.toString(latestId + 1);
            //add new task to tasks
//            Database.getInstance().addTask(new Task(id, title, description));
            //redirect back to tasks

            view.redirectToTasks();
        }
    }
}