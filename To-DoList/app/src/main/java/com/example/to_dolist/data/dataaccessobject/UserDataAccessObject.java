package com.example.to_dolist.data.dataaccessobject;

import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.Database;

import java.util.ArrayList;

public class UserDataAccessObject extends DataAccessObject<User> {

    @Override
    public boolean addItemToList(User user) {
        return false;
    }

    @Override
    public ArrayList<User> getList() {
        return null;
    }

    @Override
    public User getListItemById(String id) {
        return null;
    }

    @Override
    public boolean editListItemById(String id, User user) {
        return false;
    }

    @Override
    public boolean deleteListItemById(String id) {
        return false;
    }
}
