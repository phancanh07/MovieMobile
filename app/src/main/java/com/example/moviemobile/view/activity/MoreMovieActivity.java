package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.config.EndlessRecyclerViewScrollListener;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.movie.Example;
import com.example.moviemobile.model.movie.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreMovieActivity extends AppCompatActivity implements CallBackItem {
    private RecyclerView recyclerViewMovie;
    private List<Result> list = new ArrayList<>();
    private MovieListAdapter movieListAdapter;
    private IfMovieList ifMovie;
    private GridLayoutManager manager;
    private ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    private Toolbar toolbar;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_movie);
        initUI();

        getList(page);
        manager = new GridLayoutManager(getApplicationContext(), 2);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    getList(page);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    private void getList(int page) {
        ifMovie.getListMovie(page).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    list.addAll(response.body().getResults());
                    movieListAdapter = new MovieListAdapter(list, getApplicationContext(), MoreMovieActivity.this::onClickItem);
                    recyclerViewMovie.setLayoutManager(manager);
                    recyclerViewMovie.setHasFixedSize(true);
                    recyclerViewMovie.setAdapter(movieListAdapter);
                    movieListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    private void initUI() {
        ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        recyclerViewMovie = findViewById(R.id.list_movie);
        progressBar = findViewById(R.id.progressBarr);
        nestedScrollView = findViewById(R.id.nedted_movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Movies");

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
    public void onClickItem(int positon, String id) {
        startActivity(new Intent(this, DetailMovieActivity.class).putExtra("KEY_ID", id));
    }
}