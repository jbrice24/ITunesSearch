package com.example.itunessearch.service;

import com.example.itunessearch.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiManager {
    @GET("search")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ApiResponse> getTracks(@Query("term") String term);
}
