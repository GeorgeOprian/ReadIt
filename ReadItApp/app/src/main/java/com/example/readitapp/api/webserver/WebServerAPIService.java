package com.example.readitapp.api.webserver;

import com.example.readitapp.model.webserver.InputBookModel;
import com.example.readitapp.model.webserver.WebServerModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServerAPIService {

    @POST("/books/addBook")
    Call<String> addBook(@Body InputBookModel body);
}
