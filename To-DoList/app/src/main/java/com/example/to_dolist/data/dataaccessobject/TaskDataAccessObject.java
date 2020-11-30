package com.example.to_dolist.data.dataaccessobject;

import com.example.to_dolist.data.model.Task;
import com.example.to_dolist.data.source.Database;

import java.util.ArrayList;

public class TaskDataAccessObject extends DataAccessObject<Task> {

    @Override
    public boolean addItemToList(Task task) {
        if(getListItemById(task.getId()) == null) {
            getList().add(task);

            return true;
        }

        return false;
    }

    @Override
    public ArrayList<Task> getList() {
        return Database.getInstance().getTasks();
    }

    @Override
    public Task getListItemById(String id) {
        for (Task task : getList()) {
            if (task != null && task.getId().equalsIgnoreCase(id)) {
                return task;
            }
        }

        return null;
    }

    @Override
    public boolean editListItemById(String id, Task task) {
        int editedTaskIndex = getTaskIndexById(id);

        if (editedTaskIndex >= 0) {
            task.setId(id);
            getList().set(editedTaskIndex, task);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteListItemById(String id) {
        int deletedItemIndex = getTaskIndexById(id);

        if (deletedItemIndex >= 0) {
            getList().remove(deletedItemIndex);

            return true;
        }

        return false;
    }

    public int getTaskIndexById(String id) {
        for (int index = 0; index < getList().size(); index++) {
            Task task = getList().get(index);

            if (task != null && task.getId().equalsIgnoreCase(id)) {
                return index;
            }
        }

        return -1;
    }

    public ArrayList<Task> getUserTasks(String userId) {
        ArrayList<Task> userTasks = new ArrayList<>();

        for (Task task : getList()) {
            if (task.getUserId().equalsIgnoreCase(userId)) {
                userTasks.add(task);
            }
        }

        return userTasks;
    }
}
