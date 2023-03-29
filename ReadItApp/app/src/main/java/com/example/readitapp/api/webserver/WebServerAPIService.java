package com.example.readitapp.api.webserver;

import com.example.readitapp.model.webserver.WebServerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServerAPIService {

    @POST("/books")
    Call<WebServerModel> test(
//            @Query("q") String q,
//            @Query("key") String apiKey
    );
}
