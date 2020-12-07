package com.example.voyavue.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.voyavue.models.User;

public class UserRepo {

    private static UserRepo instance;
    private MutableLiveData<User> userData;

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }

        return instance;
    }

    public void clearUserInfo() {
        userData = null;
    }

    public MutableLiveData<User> getUser() {
        if (userData == null) {
            userData = new MutableLiveData<>();
        }
        return userData;
    }

    public void setUser(User user) {
        userData = new MutableLiveData<>();
        userData.setValue(user);
    }
}
