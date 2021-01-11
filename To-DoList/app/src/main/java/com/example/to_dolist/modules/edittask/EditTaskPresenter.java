package com.example.to_dolist.modules.edittask;

import com.example.to_dolist.data.dataaccessobject.DataAccessObject;
import com.example.to_dolist.data.dataaccessobject.TaskDataAccessObject;
import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.source.SessionRepository;

public class EditTaskPresenter implements EditTaskContract.Presenter{
    private final EditTaskContract.View view;
    private final SessionRepository<User> sessionRepository;
    private final DataAccessObject<Task> taskDAO;

    public EditTaskPresenter(EditTaskContract.View view,
                             SessionRepository<User> sessionRepository) {
        this.view = view;
        this.sessionRepository = sessionRepository;
        this.taskDAO = new TaskDataAccessObject();
    }

    @Override
    public void start() {
        if (sessionRepository.getSessionData() == null) {
            view.redirectToLogin();
        }
    }

    @Override
    public void saveEdit(final String id, final String title, final String description) {
        if (!title.equals("")) {
            String userId = Integer.toString(sessionRepository.getSessionData().getId());

            taskDAO.editListItemById(id, new Task(id, title, description, userId));
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
