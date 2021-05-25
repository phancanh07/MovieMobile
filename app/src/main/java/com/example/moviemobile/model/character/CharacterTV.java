package com.example.moviemobile.model.character;

import java.util.List;

import com.example.moviemobile.model.tv.Cast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CharacterTV {

    @SerializedName("cast")
    @Expose
    private List<com.example.moviemobile.model.tv.Cast> cast = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<com.example.moviemobile.model.tv.Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}