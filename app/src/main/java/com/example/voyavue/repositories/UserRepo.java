package com.example.voyavue.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.voyavue.api.ApiCalls;
import com.example.voyavue.api.RetroInstance;
import com.example.voyavue.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
