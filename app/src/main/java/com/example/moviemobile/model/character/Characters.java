package com.example.moviemobile.model.character;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Characters {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

}