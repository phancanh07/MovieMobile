package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.moviemobile.R;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.config.ShowToast;
import com.example.moviemobile.controller.CallBackUrl;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int REQUET_CODE = 123;
    private YouTubePlayerView playerView;
    public static final String API_KEY = "AIzaSyDNjygETaU97qp7yMzK_Gvf_wo7UxqmwDM";
    String URL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        playerView = findViewById(R.id.youtobe);
        playerView.initialize(API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        Intent intentReceived = getIntent();
        Bundle data = intentReceived.getExtras();
        youTubePlayer.cueVideo(data.getString("KEY_YOU")+"");
    }



    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUET_CODE);
        } else {
            ShowToast.showToast("Errol", this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUET_CODE) {
            playerView.initialize(API_KEY, this);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

}