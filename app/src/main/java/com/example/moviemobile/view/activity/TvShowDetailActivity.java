package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.adapter.CharacterTvAdapter;
import com.example.moviemobile.adapter.MovieListAdapter;
import com.example.moviemobile.adapter.SimilarAdapter;
import com.example.moviemobile.adapter.TVshowTopAdapter;
import com.example.moviemobile.adapter.TvshowDetailAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.config.SendID;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.CallBackItemCharacter;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.Video;
import com.example.moviemobile.model.character.CharacterTV;
import com.example.moviemobile.model.movie.Example;
import com.example.moviemobile.model.tv.Cast;
import com.example.moviemobile.model.tvshow.DetailTVShow;
import com.example.moviemobile.model.tvshow.Result;
import com.example.moviemobile.model.tvshow.Similar;
import com.example.moviemobile.model.tvshow.SimilarResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowDetailActivity extends AppCompatActivity implements CallBackItemCharacter, CallBackItem, View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imageView;
    private Button btn_review, btn_trailer;
    private RecyclerView recyclerView_DV, recyclerView_related, recyclerView, recyclerView_lq;
    private TvshowDetailAdapter adapter;
    private SimilarAdapter similaradapter;
    private CharacterTvAdapter characterTvAdapter;
    private List<DetailTVShow> tvDetails = new ArrayList<>();
    private List<Similar> similarList = new ArrayList<>();
    private TVshowTopAdapter movieListAdapter;
    IfMovieList ifMovie;
    String url = "";
    String urlPath = "";
    String urlBaner = "";
    String movieName = "";
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show);
        ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        initUI();
        Intent intentReceived = getIntent();
        Bundle data = intentReceived.getExtras();
        if (data != null) {
            title = data.getString("TV_SHOW");
            getData(data.getString("TV_SHOW"));
            getDataChacter(data.getString("TV_SHOW"));
            movieSimilar(data.getString("TV_SHOW"));
        }
        getVideo();
        btn_review.setOnClickListener(this::onClick);
        btn_trailer.setOnClickListener(this::onClick);
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
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setAdapter(adapter);
                    urlPath = detailTVShow.getPosterPath();
                    urlBaner = detailTVShow.getBackdropPath();
                    movieName = detailTVShow.getOriginalName();
                }
            }

            @Override
            public void onFailure(Call<DetailTVShow> call, Throwable t) {

            }
        });
    }

    private void getDataChacter(String id) {
        ifMovie.getCharacterTv(id).enqueue(new Callback<CharacterTV>() {
            @Override
            public void onResponse(Call<CharacterTV> call, Response<CharacterTV> response) {
                CharacterTV characterTV = response.body();
                characterTvAdapter = new CharacterTvAdapter(characterTV.getCast(), getApplicationContext(), TvShowDetailActivity.this::onClickItemCharacter);
                recyclerView_DV.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
                recyclerView_DV.setHasFixedSize(false);
                recyclerView_DV.setAdapter(characterTvAdapter);
            }

            @Override
            public void onFailure(Call<CharacterTV> call, Throwable t) {

            }
        });
    }

    private void movieSimilar(String id) {
        ifMovie.getMovieSimilarTV(id).enqueue(new Callback<SimilarResult>() {
            @Override
            public void onResponse(Call<SimilarResult> call, Response<SimilarResult> response) {
                similarList.addAll(response.body().getResults());
                similaradapter = new SimilarAdapter(getApplicationContext(), similarList, TvShowDetailActivity.this::onClickItem);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
                recyclerView_lq.setHasFixedSize(false);
                recyclerView_lq.setLayoutManager(manager);
                recyclerView_lq.setAdapter(similaradapter);
            }

            @Override
            public void onFailure(Call<SimilarResult> call, Throwable t) {

            }
        });
    }

    private void getVideo() {
        ifMovie.getDataVideoTV(title).enqueue(new Callback<Video>() {
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

    private void initUI() {
        toolbar = findViewById(R.id.toolbar_detail_tvshow);
        imageView = findViewById(R.id.image_baner_tvshow);
        recyclerView_DV = findViewById(R.id.recylerView_tv_character);
        recyclerView = findViewById(R.id.item_recylerview_tvshow);
        recyclerView_lq = findViewById(R.id.recylerView_tv_lq);
        btn_review = findViewById(R.id.btn_review_tv);
        btn_trailer = findViewById(R.id.btn_trailer_tv);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Detail TV Show");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

    }


    @Override
    public void onClickItemCharacter(int positon, String id) {
        SendID.setId(id);
        startActivity(new Intent(this, DetailCharacterActivity.class));
    }

    @Override
    public void onClickItem(int positon, String id) {
        startActivity(new Intent(getApplicationContext(), TvShowDetailActivity.class).putExtra("TV_SHOW", id));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_review_tv: {
                Bundle bundle = new Bundle();
                bundle.putString("1", urlBaner);
                bundle.putString("2", urlPath);
                bundle.putString("3", movieName);
                bundle.putString("4", title);
                bundle.putString("5", "1");
                Intent intent = new Intent(this, ReviewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            }
            case R.id.btn_trailer_tv: {
                startActivity(new Intent(getApplicationContext(), TrailerActivity.class).putExtra("KEY_YOU", url));
                break;
            }
        }
    }
}