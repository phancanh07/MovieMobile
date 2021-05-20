package com.example.moviemobile.controller;

import com.example.moviemobile.model.detail.Detail;
import com.example.moviemobile.model.movie.Example;
import com.example.moviemobile.model.rating.TopRating;
import com.example.moviemobile.model.trend.Dates;
import com.example.moviemobile.model.trend.MovieTrend;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IfMovieList {

    @GET("3/movie/upcoming?api_key=414ffc7cfe79b04554b68edfa48428d3&language=en-US")
    Call<MovieTrend> getTopMovie(@Query("page") int page);

    @GET("3/movie/top_rated?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US")
    Call<TopRating> getTopRating(@Query("page") int page);

    @GET("3/movie/{id}?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US")
    Call<Detail> getDetailMovie(@Path("id") String id);

}
