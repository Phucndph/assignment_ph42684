package com.example.assignment_ph42684;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    String DOMAIN = "http://192.168.143.179:3000/";


    @GET("/api/list")
    Call<List<sanphamModel>> getSanphams();

    @POST("/api/add")
    Call<Void> addsp(@Body sanphamModel cay);

    @DELETE("/api/delete/{id}")
    Call<Void> deletesp(@Path("id") String id);

    @PUT("/api/update/{id}")
    Call<Void> updatesp(
            @Path("id") String id,
            @Body sanphamModel cay
    );
}
