package com.example.to_dolist.modules.addtask;

import android.util.Log;

import com.example.to_dolist.data.dataaccessobject.DataAccessObject;
import com.example.to_dolist.data.dataaccessobject.TaskDataAccessObject;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.Database;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.source.SessionRepository;

import java.util.ArrayList;

public class AddTaskPresenter implements AddTaskContract.Presenter {
    private final AddTaskContract.View view;
    private final DataAccessObject<Task> taskDAO;
    private final SessionRepository<User> sessionRepository;

    public AddTaskPresenter(AddTaskContract.View view, SessionRepository<User> sessionRepository) {
        this.view = view;
        this.taskDAO = new TaskDataAccessObject();
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void start() {
        if (sessionRepository.getSessionData() == null) {
            view.redirectToLogin();
        }
    }

    @Override
    public void saveData(final String title, final String description) {
        if (!title.equals("")) {
            ArrayList<Task> tasks = (ArrayList<Task>) taskDAO.getList();
            String id = "0";
            String userId = sessionRepository.getSessionData().getId();
            Task task;

            if (tasks.size() > 0) {
                int lastItemId = Integer.parseInt(tasks.get(tasks.size() - 1).getId());

                id = Integer.toString(lastItemId + 1);
            }
            //add new task to tasks
            task = new Task(id, title, description, userId);
            taskDAO.addItemToList(task);
            task = taskDAO.getListItemById(task.getId());
            Log.d("AppDebug",
                "Added: " + task.getId() + " " + task.getTitle() + " " + task.getDescription()
            );
            //redirect back to tasks
            view.redirectToTasks();
        }
    }
}