package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.example.moviemobile.adapter.CharacterAdapter;
import com.example.moviemobile.adapter.DetailMovieAdapter;
import com.example.moviemobile.adapter.MovieListAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.CallBackItemCharacter;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.Video;
import com.example.moviemobile.model.character.Cast;
import com.example.moviemobile.model.character.Characters;
import com.example.moviemobile.model.detail.Detail;
import com.example.moviemobile.model.movie.Example;
import com.example.moviemobile.model.movie.Result;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity implements CallBackItem, CallBackItemCharacter, View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_DV, recyclerView_related;
    private DetailMovieAdapter adapter;
    private MovieListAdapter movieListAdapter;
    private Button btn_trailer, btn_review;
    private List<Detail> detailList = new ArrayList<>();
    private List<Cast> castList = new ArrayList<>();
    private List<Result> resultList = new ArrayList<>();
    String title;
    MainActivity mainActivity;
    IfMovieList ifMovie;
    String url = "";
    String urlPath = "";
    String urlBaner = "";
    String movieName = "";
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        Intent intentReceived = getIntent();
        Bundle data = intentReceived.getExtras();
        initUI();
        if (data != null) {
            title = data.getString("KEY_ID");
            getData(title);
            getCharacter(title);
            movieSimilar(title);
        } else {
//            title = mainActivity.id;
//            getData(title);
//            getCharacter(title);
//            movieSimilar(title);
        }
        getVideo();
        btn_trailer.setOnClickListener(this::onClick);
        btn_review.setOnClickListener(this::onClick);

    }


    private void movieSimilar(String id) {
        ifMovie.getMovieSimilar(id).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    Example example = response.body();
                    resultList.addAll(example.getResults());
                    movieListAdapter = new MovieListAdapter(resultList, getApplicationContext(), DetailMovieActivity.this::onClickItem);
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    recyclerView_related.setHasFixedSize(false);
                    recyclerView_related.setLayoutManager(manager);
                    recyclerView_related.setAdapter(movieListAdapter);
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }


    private void getCharacter(String id) {
        ifMovie.getCharacters(id).enqueue(new Callback<Characters>() {
            @Override
            public void onResponse(Call<Characters> call, Response<Characters> response) {
                if (response.isSuccessful()) {
                    castList.addAll(response.body().getCast());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    CharacterAdapter characterAdapter = new CharacterAdapter(castList, getApplicationContext(), DetailMovieActivity.this::onClickItemCharacter);
                    recyclerView_DV.setLayoutManager(layoutManager);
                    recyclerView_DV.setHasFixedSize(false);
                    recyclerView_DV.setAdapter(characterAdapter);
                }
            }

            @Override
            public void onFailure(Call<Characters> call, Throwable t) {

            }
        });
    }

    private void getData(String id) {
        ifMovie.getDetailMovie(id).enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if (response.isSuccessful()) {
                    Detail detail = response.body();
                    Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + detail.getBackdropPath()).into(imageView);
                    detailList.add(detail);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    adapter = new DetailMovieAdapter(detailList, getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setAdapter(adapter);
                    urlPath = detail.getPosterPath();
                    urlBaner = detail.getBackdropPath();
                    movieName = detail.getOriginalTitle();

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
        btn_trailer = findViewById(R.id.btn_trailer);
        btn_review = findViewById(R.id.btn_review);
        recyclerView_DV = findViewById(R.id.recylerView_character);
        recyclerView = findViewById(R.id.item_recylerview_movie);
        recyclerView_related = findViewById(R.id.recylerView_movie_ralated);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Detail Movies");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
    }

    @Override
    public void onClickItem(int positon, String id) {
        Intent intent = new Intent(getApplicationContext(), DetailMovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("KEY_ID", id);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @Override
    public void onClickItemCharacter(int positon, String id) {
        startActivity(new Intent(this, DetailCharacterActivity.class).putExtra("ID_CHARACTER", id));
    }

    private void getVideo() {
        ifMovie.getDataVideo(title).enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                if (response.isSuccessful()) {
                    Video video = response.body();
                    if (video.getResults().size() != 0) {
                        url = video.getResults().get(0).getKey();
                    }
                }
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == btn_trailer) {
            startActivity(new Intent(getApplicationContext(), TrailerActivity.class).putExtra("KEY_YOU", url));
        } else if (v == btn_review) {
            Bundle bundle = new Bundle();
            bundle.putString("1", urlBaner);
            bundle.putString("2", urlPath);
            bundle.putString("3", movieName);
            bundle.putString("4", title);
            bundle.putString("5", "0");
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}