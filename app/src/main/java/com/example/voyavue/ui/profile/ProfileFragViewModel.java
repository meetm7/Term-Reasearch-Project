package com.example.voyavue.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voyavue.models.User;
import com.example.voyavue.repositories.UserRepo;

public class ProfileFragViewModel extends ViewModel {

    private MutableLiveData<User> userData;
    private UserRepo uRepo;

    public void init() {
        if (uRepo != null) {
            return;
        }
        uRepo = UserRepo.getInstance();
        userData = uRepo.getUser();
        Log.d("profileFragVM", "cannot find user ");
    }

    public LiveData<User> getUser() {
        return userData;
    }

}