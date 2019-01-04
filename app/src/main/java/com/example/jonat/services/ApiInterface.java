package com.example.jonat.services;

import com.example.jonat.services.Models.Events;
import com.example.jonat.services.Models.Fighters;
import com.example.jonat.services.Models.Medias;
import com.example.jonat.services.Models.Title;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jonat on 10/8/2017.
 */

public interface ApiInterface {
    @GET("v1/us/{sort_by}")
    Call<List<Events>> getEvents(@Path("sort_by") String mSortBy);

    @GET("v1/us/fighters/{sort_by}")
    Call<List<Title>> getTitles(@Path("sort_by") String mSortBy);

    @GET("v1/us/{sort_by}")
    Call<List<Medias>> getMedia(@Path("sort_by") String mSortBy);

    @GET("v1/us/{sort_by}")
    Call<List<Fighters>> getFighters(@Path("sort_by") String mSortBy);




}