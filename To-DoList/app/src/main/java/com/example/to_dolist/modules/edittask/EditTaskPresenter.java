package com.example.to_dolist.modules.edittask;

import android.util.Log;

import com.example.to_dolist.data.database.Database;
import com.example.to_dolist.data.model.Task;

//import pens.lab.app.belajaractivity.data.model.Task;

/**
 * Created by fahrul on 13/03/19.
 */

public class EditTaskPresenter implements EditTaskContract.Presenter{
    private final EditTaskContract.View view;

    public EditTaskPresenter(EditTaskContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
    }

    @Override
    public void saveEdit(final String id, final String title, final String description){
        //save new task
        Database.getInstance().setTask(new Task(id, title, description));
        //then go back to task list
        view.redirectToTaskList();
    }

    @Override
    public void loadData(String id) {
        //load data task by id
        Task task = Database.getInstance().getTaskById(id);
        //then send data to fragment
//        Task task = new Task("3", "title of taskIndex:"+id, "description of taskIndex:"+id);
        view.showData(task);
    }

}
