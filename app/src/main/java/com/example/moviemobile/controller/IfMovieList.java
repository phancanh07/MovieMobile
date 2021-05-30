package com.example.moviemobile.controller;

import com.example.moviemobile.model.Video;
import com.example.moviemobile.model.character.CharacterTV;
import com.example.moviemobile.model.character.Characters;
import com.example.moviemobile.model.character.DetailCharacter;
import com.example.moviemobile.model.detail.Detail;
import com.example.moviemobile.model.movie.Example;
import com.example.moviemobile.model.rating.TopRating;
import com.example.moviemobile.model.trend.Dates;
import com.example.moviemobile.model.trend.MovieTrend;
import com.example.moviemobile.model.tvshow.DetailTVShow;
import com.example.moviemobile.model.tvshow.TvTop;

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

    //https://api.themoviedb.org/3/movie/460465/credits?api_key=414ffc7cfe79b04554b68edfa48428d3
    @GET("3/movie/{id}/credits?api_key=414ffc7cfe79b04554b68edfa48428d3")
    Call<Characters> getCharacters(@Path("id") String id);

    //https://api.themoviedb.org/3/person/136347?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US
    @GET("3/person/{id}?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US")
    Call<DetailCharacter> getCharactersDetail(@Path("id") String id);

    //https://api.themoviedb.org/3/movie/460465/similar?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=vi-VN&page=1
    @GET("3/movie/{id}/similar?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US&page=1")
    Call<Example> getMovieSimilar(@Path("id") String id);

    //https://api.themoviedb.org/3/tv/top_rated?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US&page=1
    @GET("3/tv/top_rated?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US")
    Call<TvTop> getTopTvshow(@Query("page") int page);

    //https://api.themoviedb.org/3/discover/tv?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US&sort_by=popularity.desc&page=1&timezone=America%2FNew_York&include_null_first_air_dates=false&with_watch_monetization_types=flatraten
    @GET("3/discover/tv?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US&sort_by=popularity.desc&timezone=America%2FNew_York&include_null_first_air_dates=false&with_watch_monetization_types=flatraten")
    Call<TvTop> getTvTrending(@Query("page") int page);

    //https://api.themoviedb.org/3/tv/88396?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=iso_639_1
    @GET("3/tv/{id}?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=iso_639_1")
    Call<DetailTVShow> getDetailTV(@Path("id") String id);

    //https://api.themoviedb.org/3/tv/88396/aggregate_credits?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US
    @GET("3/tv/{id}/aggregate_credits?api_key=7ca9a1c101d5fe4355292c4d92a72f75&language=en-US")
    Call<CharacterTV> getCharacterTv(@Path("id") String id);
    @GET("3/movie/{id}/videos?api_key=414ffc7cfe79b04554b68edfa48428d3")
    Call<Video> getDataVideo(@Path("id") String id);

}
