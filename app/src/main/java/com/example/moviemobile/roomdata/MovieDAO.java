package com.example.moviemobile.roomdata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviemobile.model.Favorite;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert
    void insertMovie(Favorite favorite);

    @Query("DELETE FROM table_movie WHERE idMovie=:idmovie")
    void delete(int idmovie);

    @Query("SELECT*FROM table_movie")
    List<Favorite> getList();

    @Query("SELECT * FROM table_movie  where idMovie  =:idmovie")
    boolean checkMovie(int idmovie);

    @Query("SELECT * FROM table_movie where idMovie=:id")
    List<Favorite> checkMovieExit(String id);

}
