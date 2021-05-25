package com.example.moviemobile.model.character;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Job {

    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("episode_count")
    @Expose
    private Integer episodeCount;

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

}