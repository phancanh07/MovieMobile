package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.adapter.MovieListAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.Review.Review;
import com.example.moviemobile.model.movie.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {
    private ImageView banner, posster;
    private TextView txt_name, txt_review;
    private IfMovieList ifMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        banner = findViewById(R.id.img_baner_reivew);
        posster = findViewById(R.id.img_poster_review);
        txt_name = findViewById(R.id.txt_titile);
        txt_review = findViewById(R.id.txt_review);
        ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        Intent intentReceived = getIntent();
        Bundle data = intentReceived.getExtras();
        String baner = data.getString("1");
        String poster = data.getString("2");
        String title = data.getString("3");
        String id = data.getString("4");
        txt_name.setText(title);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + baner).into(banner);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500" + poster).into(posster);
        getReview(id);
    }

    private void getReview(String id) {
        ifMovie.getDataReview(Integer.parseInt(id)).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    int size=response.body().getResults().size();
                    if(size!=0){
                        txt_review.setText(response.body().getResults().get(0).getContent());
                    }
                    else {
                        txt_review.setText("Chưa cập nhật review");
                    }

                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

}