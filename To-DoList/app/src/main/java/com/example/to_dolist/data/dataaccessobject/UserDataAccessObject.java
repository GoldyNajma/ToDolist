package com.example.to_dolist.data.dataaccessobject;

import com.example.to_dolist.data.model.User;
import com.example.to_dolist.data.source.Database;

import java.util.ArrayList;

public class UserDataAccessObject { }

//public class UserDataAccessObject extends DataAccessObject<User> {
//
//    @Override
//    public boolean addItemToList(User user) {
//        if(getListItemById(user.getId()) == null) {
//            getList().add(user);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public ArrayList<User> getList() {
//        return Database.getInstance().getUsers();
//    }
//
//    @Override
//    public User getListItemById(String id) {
//        for (User user : getList()) {
//            if (user != null && user.getId().equalsIgnoreCase(id)) {
//                return user;
//            }
//        }
//
//        return null;
//    }
//
//    @Override
//    public boolean editListItemById(String id, User user) {
//        int editedUserIndex = getUserIndexById(id);
//
//        if (editedUserIndex >= 0) {
//            user.setId(id);
//            getList().set(editedUserIndex, user);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean deleteListItemById(String id) {
//        int deletedUserIndex = getUserIndexById(id);
//
//        if (deletedUserIndex >= 0) {
//            getList().remove(deletedUserIndex);
//
//            return true;
//        }
//
//        return false;
//    }
//
//    public int getUserIndexById(String id) {
//        for (int index = 0; index < getList().size(); index++) {
//            User user = getList().get(index);
//
//            if (user != null && user.getId().equalsIgnoreCase(id)) {
//                return index;
//            }
//        }
//
//        return -1;
//    }
//
//    public User validateUser(String email, String password) {
//        for (User user : getList()) {
//            if (user.getEmail().equalsIgnoreCase(email)
//                    && user.getPassword().equalsIgnoreCase(password)) {
//                return user;
//            }
//        }
//
//        return null;
//    }
//}