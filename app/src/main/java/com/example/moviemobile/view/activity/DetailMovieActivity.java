package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.adapter.DetailMovieAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.detail.Detail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity  {
    private Toolbar toolbar;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private DetailMovieAdapter adapter;
    private List<Detail> detailList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        initUI();
        getData();

    }

    private void getData() {

        IfMovieList ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovie.getDetailMovie("399566").enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if (response.isSuccessful()) {
                    Detail detail = response.body();
                    detailList.add(detail);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    adapter = new DetailMovieAdapter(detailList, getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

            }
        });
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar_detail);
        imageView = findViewById(R.id.image_baner);
        recyclerView = findViewById(R.id.item_recylerview_movie);
        Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/original/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg").into(imageView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Detail");
        toolbar.setTitleTextColor(Color.WHITE);
    }

}