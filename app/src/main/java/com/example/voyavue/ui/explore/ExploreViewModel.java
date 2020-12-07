package com.example.voyavue.ui.explore;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voyavue.api.ApiCalls;
import com.example.voyavue.api.RetroInstance;
import com.example.voyavue.models.Post;
import com.example.voyavue.repositories.UserRepo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Post>> mPost;

    public ExploreViewModel() {
        mPost = new MutableLiveData<>();
        fetchPost();
    }

    public void fetchPost() {

        UserRepo uRepo = UserRepo.getInstance();

        ApiCalls apiCall = RetroInstance.getRetrofitClient().create(ApiCalls.class);
        Call<ArrayList<Post>> call = apiCall.getAllPublicPosts();
        call.enqueue(new Callback<ArrayList<Post>>() {
            @Override
            public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                mPost.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
                Log.d("HVM:", "onFailure: " + t.getMessage());
            }
        });

    }

    public LiveData<ArrayList<Post>> getPosts() {
        return mPost;
    }
}