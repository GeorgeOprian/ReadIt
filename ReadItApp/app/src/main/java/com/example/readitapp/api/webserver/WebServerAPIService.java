package com.example.readitapp.api.webserver;

import com.example.readitapp.model.webserver.BookDto;
import com.example.readitapp.model.webserver.InputBookModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebServerAPIService {

    @POST("/books/addBook")
    Call<BookDto> addBook(@Body InputBookModel body);
}
