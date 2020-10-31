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

    public MutableLiveData<User> getUserByEmail(String email) {
        if (userData == null) {
            userData = new MutableLiveData<>();
            fetchUserInfo(email);
        }
        return userData;
    }

    public void fetchUserInfo(String email) {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<User> call = apiCall.getUserDetails(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userData.postValue(response.body());
                Log.d("Response", "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("UserRepo:", "Cannot get user data");
            }
        });
    }

    public void addUserNewUser(User userInfo) {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<User> call = apiCall.addUser(userInfo);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("UserRepo", "Cannot create user");
            }
        });
    }
}
