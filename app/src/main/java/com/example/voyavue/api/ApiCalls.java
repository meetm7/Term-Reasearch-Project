package com.example.voyavue.api;

import com.example.voyavue.models.AdditionProfileInfo;
import com.example.voyavue.models.Post;
import com.example.voyavue.models.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiCalls {
    @GET("userInfo")
    Call<User> getUserDetails(@Query(value = "email") String email);

    @POST("addUser")
    Call<User> addUser(@Body User user);

    @POST("addPost")
    Call<Post> addPost(@Body Post post);

    @GET("getPostsByUser")
    Call<ArrayList<Post>> getPostsByUser(@Query(value = "userName") String userName);

    @GET("getAllPosts")
    Call<List<Post>> getAllPosts();

    @GET("getUserProfileInfo")
    Call<AdditionProfileInfo> getUserProfileInfoByUserName(@Query(value = "userName") String userName);

    @PUT("updateUserInfo")
    Call<User> updateUserInfo(@Body User user);
}
