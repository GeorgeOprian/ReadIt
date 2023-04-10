package com.example.readitapp.api.webserver;

import com.example.readitapp.model.webserver.book.output.BookDto;
import com.example.readitapp.model.webserver.book.input.InputBookModel;
import com.example.readitapp.model.webserver.book.output.PageDto;
import com.example.readitapp.model.webserver.user.input.UserCreateDto;
import com.example.readitapp.model.webserver.user.output.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebServerAPIService {

    @POST("/books/addBook")
    Call<BookDto> addBook(@Body InputBookModel body);

    @POST("/users/addUser")
    Call<UserDto> addUser(@Body UserCreateDto body);

    int DEFAULT_PAGE_SIZE = 4;

    @GET("/books/all")
    Call<PageDto<BookDto>> getAllBooks(@Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize, @Query("sortBy") String sortBy);
}
