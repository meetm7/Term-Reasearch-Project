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
    private MutableLiveData<List<User>> userData;

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }

        return instance;
    }

    public MutableLiveData<List<User>> getUser() {
        if (userData == null) {
            userData = new MutableLiveData<>();
            fetchUserFromApi();
        }
        return userData;
    }

    public void fetchUserFromApi() {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<List<User>> call = apiCall.getUserDetails();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userData.postValue(response.body());
                Log.d("Response", "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("UserRepo:", "Cannot get user data");
            }
        });
    }
}
