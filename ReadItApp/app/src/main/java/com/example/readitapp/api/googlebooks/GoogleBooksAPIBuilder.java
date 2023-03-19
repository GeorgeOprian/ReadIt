package com.example.readitapp.api.googlebooks;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleBooksAPIBuilder {

    private static GoogleBooksAPIService apiService;
    public final static String BASE_URL = "https://www.googleapis.com/";
    public final static String API_KEY = "AIzaSyAA8BQ_iY7IFlU6XXcpCCA1rIDpkVAFwGQ";

    public static GoogleBooksAPIService getInstance() {
        if (apiService == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(GoogleBooksAPIService.class);
        }
        return apiService;
    }

}
