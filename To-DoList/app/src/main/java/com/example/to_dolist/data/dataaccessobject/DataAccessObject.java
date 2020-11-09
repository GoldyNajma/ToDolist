package com.example.to_dolist.data.dataaccessobject;

import com.example.to_dolist.data.source.Database;

import java.util.ArrayList;

public abstract class DataAccessObject<T> {
    Database database;

    public DataAccessObject() {
        database = Database.getInstance();
    }

    public abstract boolean addItemToList(T t);

    public abstract ArrayList<T> getList();

    public abstract T getListItemById(String id);

    public abstract boolean editListItemById(String id, T t);

    public abstract boolean deleteListItemById(String id);
}
