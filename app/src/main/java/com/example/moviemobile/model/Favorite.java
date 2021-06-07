package com.example.moviemobile.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_movie")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int type;
    private int idMovie;
    private String url;
    private String title;
    private double voteAverage;


    public Favorite() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Favorite(int id, int type, int idMovie, String url, String title, double voteAverage) {
        this.id = id;
        this.type = type;
        this.idMovie = idMovie;
        this.url = url;
        this.title = title;
        this.voteAverage = voteAverage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}

