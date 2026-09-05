package com.example.newspaper.serviceapi;

import com.example.newspaper.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("search")
    Call<Result> getNews(@Query("apiKey") String apiKey, @Query("category") String category);   //method signature
}
