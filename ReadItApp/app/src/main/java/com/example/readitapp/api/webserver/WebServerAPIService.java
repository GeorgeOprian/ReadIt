package com.example.readitapp.api.webserver;

import com.example.readitapp.model.webserver.book.input.BookDto;
import com.example.readitapp.model.webserver.book.input.BookListDto;
import com.example.readitapp.model.webserver.book.output.OutputBookModel;
import com.example.readitapp.model.webserver.book.input.PageDto;
import com.example.readitapp.model.webserver.user.input.UserCreateDto;
import com.example.readitapp.model.webserver.user.output.UserDto;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServerAPIService {

    @POST("/books/addBook")
    Call<BookListDto> addBook(@Body OutputBookModel body);

    @POST("/users/google-signin")
    Call<UserDto> addUser(@Body String idToken);

    int DEFAULT_PAGE_SIZE = 4;

    @GET("/books/all")
    Call<PageDto<BookListDto>> getBookById(@Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize, @Query("sortBy") String sortBy);

    @GET("/books")
    Call<BookDto> getBookById(@Query("bookId") Integer bookId);

    @DELETE("/books/delete")
    Call<Void> deleteBook(@Query("bookId") Integer bookId);

    @PATCH("/books/updatebook/{bookId}")
    Call<BookDto> updateBook(@Path("bookId") Integer bookId, @Body Map<String, Object> book);
}
