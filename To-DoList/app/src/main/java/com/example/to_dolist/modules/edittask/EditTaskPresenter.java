package com.example.to_dolist.modules.edittask;

import com.example.to_dolist.data.dataaccessobject.DataAccessObject;
import com.example.to_dolist.data.dataaccessobject.TaskDataAccessObject;
import com.example.to_dolist.data.source.Database;
import com.example.to_dolist.data.model.Task;

//import pens.lab.app.belajaractivity.data.model.Task;

/**
 * Created by fahrul on 13/03/19.
 */

public class EditTaskPresenter implements EditTaskContract.Presenter{
    private final EditTaskContract.View view;
    private final DataAccessObject<Task> taskDAO;

    public EditTaskPresenter(EditTaskContract.View view) {
        this.view = view;
        this.taskDAO = new TaskDataAccessObject();
    }

    @Override
    public void start() {
    }

    @Override
    public void saveEdit(final String id, final String title, final String description) {
        if (!title.equals("")) {
            taskDAO.editListItemById(id, new Task(id, title, description));
        }
        view.redirectToTaskList();
    }

    @Override
    public void loadData(String id) {
        //load data task by id
        Task task = taskDAO.getListItemById(id);
        //then send data to fragment
//        Task task = new Task("3", "title of taskIndex:"+id, "description of taskIndex:"+id);
        view.showData(task);
    }

}
