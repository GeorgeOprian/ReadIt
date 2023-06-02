package com.example.readitapp.api.webserver;

import com.example.readitapp.model.webserver.LocalitateDto;
import com.example.readitapp.model.webserver.JudetDto;
import com.example.readitapp.model.webserver.SubscriptionDto;
import com.example.readitapp.model.webserver.UserAddressInputDto;
import com.example.readitapp.model.webserver.book.request.BookRentRequestDto;
import com.example.readitapp.model.webserver.book.request.BookUserRequestDto;
import com.example.readitapp.model.webserver.book.response.BookRentResponseDto;
import com.example.readitapp.model.webserver.book.response.BookDto;
import com.example.readitapp.model.webserver.book.response.BookListDto;
import com.example.readitapp.model.webserver.book.response.PageDto;
import com.example.readitapp.model.webserver.review.BookReviewDto;
import com.example.readitapp.model.webserver.user.input.UserCreateDto;
import com.example.readitapp.model.webserver.user.output.UserDto;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServerAPIService {

    @POST("/books/addBook")
    Call<BookDto> addBook(@Body BookDto body);

    @POST("/users/addUser")
    Call<UserDto> addUser(@Body UserCreateDto userCreateDto);

    int DEFAULT_PAGE_SIZE = 4;

    @GET("/books/all")
    Call<PageDto<BookListDto>> getBookById(@Query("pageNumber") Integer pageNumber, @Query("pageSize") Integer pageSize, @Query("sortBy") String sortBy);

    @GET("/books")
    Call<BookDto> getBookById(@Query("bookId") Integer bookId, @Query("email") String email);

    @DELETE("/books/delete")
    Call<Void> deleteBook(@Query("bookId") Integer bookId);

    @PATCH("/books/updatebook/{bookId}")
    Call<BookDto> updateBook(@Path("bookId") Integer bookId, @Body Map<String, Object> book);

    @POST("/bookrental/rent")
    Call<BookRentResponseDto> createSubscription(@Body BookRentRequestDto dto);

    @GET("/subscriptions/currentsubscription")
    Call<SubscriptionDto> getAvailability(@Query("email") String email);

    @POST("/subscriptions/add")
    Call<SubscriptionDto> createSubscription(@Body SubscriptionDto subscriptionDto);

    @GET("/addresses/judete")
    Call<List<JudetDto>> getJudete();

    @GET("/addresses/localitati")
    Call<List<LocalitateDto>> getLocalitati();

    @POST("/addresses/address")
    Call<UserDto> addAddress(@Body UserAddressInputDto userAddressInputDto);

    @GET("/users/details")
    Call<UserDto> getUserDetails(@Query("email") String email);

    @POST("/bookrental/return")
    Call<List<BookRentResponseDto>> returnBook(@Query("rentId") Integer rentId);

    @GET("/bookrental/notreturnedbooks")
    Call<List<BookRentResponseDto>> loadNotReturnedBooks(@Query("email") String email);

    @GET("/bookrental/returnedbooks")
    Call<List<BookRentResponseDto>> loadReturnedBooks(@Query("email") String email);

    @POST("/wishlist/addbook")
    Call<BookDto> addBookToWishList(@Body BookUserRequestDto bookUserRequestDto);

    @GET("/wishlist")
    Call<List<BookListDto>> getWishList(@Query("email") String email);

    @DELETE("/wishlist/deletebook")
    Call<List<BookListDto>> removeBookFromWishList(@Query("bookId") Integer bookId, @Query("email") String email);

    @GET("/reviews")
    Call<List<BookReviewDto>> loadReviews(@Query("bookId") Integer bookId);
}
