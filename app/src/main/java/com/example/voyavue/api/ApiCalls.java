package com.example.voyavue.api;

import com.example.voyavue.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiCalls {
    @GET("userInfo")
    Call<User> getUserDetails(@Query(value = "email") String email);

    @POST("addUser")
    Call<User> addUser(@Body User user);
}
