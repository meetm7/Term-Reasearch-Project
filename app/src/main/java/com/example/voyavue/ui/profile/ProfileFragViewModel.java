package com.example.voyavue.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voyavue.api.ApiCalls;
import com.example.voyavue.api.RetroInstance;
import com.example.voyavue.models.AdditionProfileInfo;
import com.example.voyavue.models.User;
import com.example.voyavue.repositories.UserRepo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragViewModel extends ViewModel {

    private MutableLiveData<User> userData;

    private MutableLiveData<AdditionProfileInfo> additionProfileInfoMutableLiveData;

    private UserRepo uRepo;

    public void init() {
        if (uRepo != null) {
            return;
        }
        uRepo = UserRepo.getInstance();
        userData = uRepo.getUser();

        additionProfileInfoMutableLiveData = new MutableLiveData<>();
        fetchProfileInfo(userData.getValue().getUserName());
    }

    public LiveData<User> getUser() {
        return userData;
    }

    public LiveData<AdditionProfileInfo> getAdditionalInfo() {
        return additionProfileInfoMutableLiveData;
    }

    public void fetchProfileInfo(String userName) {
        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<AdditionProfileInfo> call = apiCall.getUserProfileInfoByUserName(userName);
        call.enqueue(new Callback<AdditionProfileInfo>() {
            @Override
            public void onResponse(Call<AdditionProfileInfo> call, Response<AdditionProfileInfo> response) {
                if (response.body() != null) {
                    Log.d("Response", "onResponse: " + response.body().toString());
                    additionProfileInfoMutableLiveData.setValue(response.body());
                    Log.d("Response", "onResponse: " + response.body().toString());
                } else {
                    Log.d("ProfileFrag:", "Cannot get user data");
                }
            }

            @Override
            public void onFailure(Call<AdditionProfileInfo> call, Throwable t) {
                Log.d("ProfileFrag:", "Cannot get data");
            }
        });
    }

}