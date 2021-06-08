package com.example.moviemobile.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.example.moviemobile.adapter.MovieListAdapter;
import com.example.moviemobile.adapter.PhotoSliderAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.PhotoSlider;
import com.example.moviemobile.model.movie.Result;
import com.example.moviemobile.model.rating.TopRating;
import com.example.moviemobile.model.trend.MovieTrend;
import com.example.moviemobile.view.activity.DetailMovieActivity;
import com.example.moviemobile.view.activity.MoreMovieActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements CallBackItem, View.OnClickListener {


    private ProgressBar progressBar;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoSliderAdapter photoSliderAdapter;
    private Timer timer;
    private List<PhotoSlider> list;
    private MovieListAdapter movieListAdapter;
    private RecyclerView recyclerView, recyclerViewRating;
    int firsVisitableItemCount;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManagerr;
    int totalitemCount;
    int visitableCount;
    int previousTotal;
    public static int PAGE = 1;
    public static int PAGER = 1;
    boolean load = true;
    List<Result> trendList = new ArrayList<>();
    List<Result> ratingList = new ArrayList<>();
    private TextView seemore1, seemore2;

    MainActivity mainActivity;

    // TODO: Rename and change types of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //ca-app-pub-7345408921633152/4806326048
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI(view);
        getMovieTrending(PAGE);
        getMovieRating(PAGER);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView v, int dx, int scrollY) {
                super.onScrolled(recyclerView, dx, scrollY);
                firsVisitableItemCount = layoutManager.findFirstCompletelyVisibleItemPosition();
                totalitemCount = layoutManager.getItemCount();
                visitableCount = layoutManager.getChildCount();
                if (load) {
                    if (totalitemCount > previousTotal) {
                        previousTotal = totalitemCount;
                        PAGE++;
                        load = false;
                    }
                } else if (!load && (firsVisitableItemCount + visitableCount) >= totalitemCount) {
                    getMovieTrending(PAGE);
                    load = true;
                }
            }
        });
        seemore1.setOnClickListener(this::onClick);
        seemore2.setOnClickListener(this::onClick);


        return view;
    }


    private void initUI(View view) {
        viewPager = view.findViewById(R.id.viewPager_Home);
        circleIndicator = view.findViewById(R.id.circleIndicatior_home);
        recyclerView = view.findViewById(R.id.recyclerView_trend_movie);
        recyclerViewRating = view.findViewById(R.id.recyclerView_rating_movie);
        seemore1 = view.findViewById(R.id.seemoremovie);
        seemore2 = view.findViewById(R.id.seemoremovie2);
        progressBar = view.findViewById(R.id.progressBarr);
        list = getList();
        photoSliderAdapter = new PhotoSliderAdapter(getContext(), getList());
        viewPager.setAdapter(photoSliderAdapter);
        circleIndicator.setViewPager(viewPager);
        photoSliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlider();
    }

    private void getMovieTrending(int page) {
        IfMovieList ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovie.getTopMovie(page).enqueue(new Callback<MovieTrend>() {
            @Override
            public void onResponse(Call<MovieTrend> call, Response<MovieTrend> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    trendList = response.body().getResults();
                    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    movieListAdapter = new MovieListAdapter(response.body().getResults(), getContext(), HomeFragment.this::onClickItem);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(movieListAdapter);
                    movieListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieTrend> call, Throwable t) {

            }
        });
    }

    private void getMovieRating(int page) {
        IfMovieList ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovie.getTopRating(page).enqueue(new Callback<TopRating>() {

            @Override
            public void onResponse(Call<TopRating> call, Response<TopRating> response) {
                if (response.isSuccessful()) {
                    ratingList.addAll(response.body().getResults());
                    progressBar.setVisibility(View.GONE);
                    movieListAdapter = new MovieListAdapter(ratingList, getContext(), HomeFragment.this::onClickItem);
                    layoutManagerr = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewRating.setLayoutManager(layoutManagerr);
                    recyclerViewRating.setHasFixedSize(false);
                    recyclerViewRating.setAdapter(movieListAdapter);
                    movieListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<TopRating> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

    private List<PhotoSlider> getList() {
        List<PhotoSlider> sliderList = new ArrayList<>();
        sliderList.add(new PhotoSlider(R.drawable.avengerr));
        sliderList.add(new PhotoSlider(R.drawable.doremon));
        sliderList.add(new PhotoSlider(R.drawable.hobit));
        sliderList.add(new PhotoSlider(R.drawable.inception));
        return sliderList;
    }

    private void autoSlider() {
        if (list == null || list.isEmpty() || viewPager == null) {
            return;
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int curenItem = viewPager.getCurrentItem();
                        int totalItem = list.size() - 1;
                        if (curenItem < totalItem) {
                            curenItem++;
                            viewPager.setCurrentItem(curenItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 2000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    @Override
    public void onClickItem(int positon, String id) {
        mainActivity.id = id;
        Intent intent = new Intent(getContext(), DetailMovieActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("KEY_ID", id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getContext(), MoreMovieActivity.class));
    }
}