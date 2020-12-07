package com.example.voyavue.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {
    public static String BASE_URL = "http://3.81.36.251:8080/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
