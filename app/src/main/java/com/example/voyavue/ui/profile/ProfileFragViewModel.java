package com.example.voyavue.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voyavue.models.User;
import com.example.voyavue.repositories.UserRepo;

import java.util.List;

public class ProfileFragViewModel extends ViewModel {

    private MutableLiveData<List<User>> userData;
    private UserRepo uRepo;

    public void init() {
        if (uRepo != null) {
            return;
        }
        uRepo = UserRepo.getInstance();
        userData = uRepo.getUser();
    }

    public LiveData<List<User>> getUser() {
        return userData;
    }

//    public void makeApiCall() {
//        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
//        Call<List<User>> call = apiCall.getUserDetails();
//        call.enqueue(new Callback<List<User>>() {
//            @Override
//            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                mText.postValue(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<List<User>> call, Throwable t) {
//                mText.setValue(t.getMessage());
//            }
//        });
//    }

}