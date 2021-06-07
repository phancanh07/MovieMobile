package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.moviemobile.R;
import com.example.moviemobile.adapter.MovieListAdapter;
import com.example.moviemobile.adapter.TVshowTopAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.config.EndlessRecyclerViewScrollListener;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.CallbackTV;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.tvshow.Result;
import com.example.moviemobile.model.tvshow.TvTop;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreTvActivity extends AppCompatActivity implements CallbackTV {
    private RecyclerView recyclerViewMovie;
    private List<Result> list = new ArrayList<>();
    private TVshowTopAdapter movieListAdapter;
    private IfMovieList ifMovie;
    private GridLayoutManager manager;
    private int page = 1;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tv);
        initUI();
        manager = new GridLayoutManager(getApplicationContext(), 2);
        getListTV(page);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    getListTV(page);
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    private void getListTV(int page) {
        ifMovie.getListTV(page).enqueue(new Callback<TvTop>() {
            @Override
            public void onResponse(Call<TvTop> call, Response<TvTop> response) {
                list.addAll(response.body().getResults());
                movieListAdapter = new TVshowTopAdapter(list, getApplicationContext(), MoreTvActivity.this::onclick);
                recyclerViewMovie.setLayoutManager(manager);
                recyclerViewMovie.setAdapter(movieListAdapter);
            }

            @Override
            public void onFailure(Call<TvTop> call, Throwable t) {

            }
        });

    }

    private void initUI() {
        recyclerViewMovie = findViewById(R.id.list_tv);
        nestedScrollView = findViewById(R.id.nedted_tv);
        progressBar = findViewById(R.id.progesbarTV);
        ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("TVShow");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onclick(String postion) {
        startActivity(new Intent(this, TvShowDetailActivity.class).putExtra("TV_SHOW", postion));
    }
}