package com.example.readitapp.api.googlebooks;

import com.example.readitapp.model.googlebooks.VolumesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksAPIService {

    //q=flowers+inauthor:keyes

    @GET("/books/v1/volumes")
    Call<VolumesResponse> getVolumes(
            @Query("q") String q,
            @Query("key") String apiKey
    );

}
