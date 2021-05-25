package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.adapter.CharacterTvAdapter;
import com.example.moviemobile.adapter.TvshowDetailAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.character.CharacterTV;
import com.example.moviemobile.model.tv.Cast;
import com.example.moviemobile.model.tvshow.DetailTVShow;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailActivity extends AppCompatActivity implements CallBackItem {
    private Toolbar toolbar;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_DV, recyclerView_related;
    private TvshowDetailAdapter adapter;
    private CharacterTvAdapter characterTvAdapter;
    private List<DetailTVShow> tvDetails = new ArrayList<>();
    private List<Cast> castList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);
        initUI();
        getData("88396");
        getDataChacter("88396");
    }

    private void getData(String id) {
        IfMovieList ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovie.getDetailTV(id).enqueue(new Callback<DetailTVShow>() {
            @Override
            public void onResponse(Call<DetailTVShow> call, Response<DetailTVShow> response) {
                if (response.isSuccessful()) {
                    DetailTVShow detailTVShow = response.body();
                    tvDetails.add(detailTVShow);
                    Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + tvDetails.get(0).getBackdropPath()).into(imageView);
                    adapter = new TvshowDetailAdapter(tvDetails, getApplicationContext());
                    Log.d("NAME", tvDetails.get(0).getName());
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DetailTVShow> call, Throwable t) {

            }
        });
    }

    private void getDataChacter(String id) {
        IfMovieList ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovie.getCharacterTv(id).enqueue(new Callback<CharacterTV>() {
            @Override
            public void onResponse(Call<CharacterTV> call, Response<CharacterTV> response) {
                CharacterTV characterTV = response.body();
                characterTvAdapter = new CharacterTvAdapter(characterTV.getCast(), getApplicationContext());
                recyclerView_DV.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false));
                recyclerView_DV.setHasFixedSize(false);
                recyclerView_DV.setAdapter(characterTvAdapter);
            }

            @Override
            public void onFailure(Call<CharacterTV> call, Throwable t) {

            }
        });
    }

    private void initUI() {
        toolbar = findViewById(R.id.toolbar_detail_tvshow);
        imageView = findViewById(R.id.image_baner_tvshow);
        recyclerView_DV = findViewById(R.id.recylerView_tv_character);
        recyclerView = findViewById(R.id.item_recylerview_tvshow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Detail TV Show");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

    }

    @Override
    public void onClickItem(int positon, String id) {

    }
}