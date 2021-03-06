package com.example.moviemobile.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemobile.R;
import com.example.moviemobile.adapter.MovieListAdapter;
import com.example.moviemobile.adapter.PhotoSliderAdapter;
import com.example.moviemobile.adapter.TVshowTopAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.CallbackTV;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.PhotoSlider;
import com.example.moviemobile.model.movie.Example;
import com.example.moviemobile.model.trend.MovieTrend;
import com.example.moviemobile.model.tvshow.Result;
import com.example.moviemobile.model.tvshow.TvTop;
import com.example.moviemobile.view.activity.MoreTvActivity;
import com.example.moviemobile.view.activity.TvShowDetailActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TVShowFragment extends Fragment implements CallbackTV, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private Timer timer;
    private List<PhotoSlider> list;
    private List<Result> resultList;
    private PhotoSliderAdapter photoSliderAdapter;
    private RecyclerView recyclerView, recyclerViewtv_trend;
    private TextView seemore1, seemore2;
    private TVshowTopAdapter movieListAdapter;
    private InterstitialAd mInterstitialAd;
    public TVShowFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_t_v_show, container, false);
        initUI(view);

        getMovieTrending(1);
        getTVshowtrending(1);
        seemore1.setOnClickListener(this::onClick);
        seemore2.setOnClickListener(this::onClick);

        return view;
    }





    private void initUI(View view) {
        viewPager = view.findViewById(R.id.viewPager_tvshow);
        seemore1 = view.findViewById(R.id.seemoretv1);
        seemore2 = view.findViewById(R.id.seemoretv2);
        recyclerViewtv_trend = view.findViewById(R.id.tv_show_trending);
        recyclerView = view.findViewById(R.id.tv_showe_top);
        circleIndicator = view.findViewById(R.id.circleIndicatior_tvshow);
        list = getList();
        photoSliderAdapter = new PhotoSliderAdapter(getContext(), getList());
        viewPager.setAdapter(photoSliderAdapter);
        circleIndicator.setViewPager(viewPager);
        photoSliderAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlider();

    }

    private List<PhotoSlider> getList() {
        List<PhotoSlider> sliderList = new ArrayList<>();
        sliderList.add(new PhotoSlider(R.drawable.soldier));
        sliderList.add(new PhotoSlider(R.drawable.wandavission));
        sliderList.add(new PhotoSlider(R.drawable.baner1));
        sliderList.add(new PhotoSlider(R.drawable.baner4));
        return sliderList;
    }

    private void getMovieTrending(int page) {
        IfMovieList ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovie.getTopTvshow(page).enqueue(new Callback<TvTop>() {
            @Override
            public void onResponse(Call<TvTop> call, Response<TvTop> response) {
                if (response.isSuccessful()) {
                    TvTop tvTop = response.body();
                    movieListAdapter = new TVshowTopAdapter(tvTop.getResults(), getContext(), TVShowFragment.this::onclick);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setAdapter(movieListAdapter);
                }
            }

            @Override
            public void onFailure(Call<TvTop> call, Throwable t) {

            }
        });
    }

    private void getTVshowtrending(int page) {
        IfMovieList ifMovie = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovie.getTvTrending(page).enqueue(new Callback<TvTop>() {
            @Override
            public void onResponse(Call<TvTop> call, Response<TvTop> response) {
                if (response.isSuccessful()) {
                    TvTop tvTop = response.body();
                    movieListAdapter = new TVshowTopAdapter(tvTop.getResults(), getContext(), TVShowFragment.this::onclick);
                    recyclerViewtv_trend.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                    recyclerViewtv_trend.setHasFixedSize(false);
                    recyclerViewtv_trend.setAdapter(movieListAdapter);
                }
            }

            @Override
            public void onFailure(Call<TvTop> call, Throwable t) {

            }
        });
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
    public void onclick(String postion) {
        startActivity(new Intent(getContext(), TvShowDetailActivity.class).putExtra("TV_SHOW", postion));
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getContext(), MoreTvActivity.class));

    }
}