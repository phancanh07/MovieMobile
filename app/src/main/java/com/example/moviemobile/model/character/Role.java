package com.example.moviemobile.model.character;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Role {

    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

}