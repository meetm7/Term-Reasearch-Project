package com.example.voyavue.api;

import com.example.voyavue.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {
    @GET("users")
    Call<List<User>> getUserDetails();
}
