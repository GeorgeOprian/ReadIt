package com.example.readitapp.api.webserver;

import com.example.readitapp.model.webserver.WebServerModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebServerAPIService {

    @GET("/test")
    Call<WebServerModel> test(
//            @Query("q") String q,
//            @Query("key") String apiKey
    );
}
