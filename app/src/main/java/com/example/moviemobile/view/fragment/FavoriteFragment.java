package com.example.moviemobile.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.moviemobile.MainActivity;
import com.example.moviemobile.R;
import com.example.moviemobile.adapter.FavoriteAdapter;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.CallBackUrl;
import com.example.moviemobile.model.Favorite;
import com.example.moviemobile.roomdata.MovieDataBase;
import com.example.moviemobile.view.activity.DetailMovieActivity;
import com.example.moviemobile.view.activity.TvShowDetailActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout layout;
    public static final int WHITE = Color.WHITE;
    private RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Favorite> favoriteList;
    private FavoriteAdapter favoriteAdapter;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        initUI(view);
        setData();
        return view;
    }

    private void setData() {
        favoriteList = MovieDataBase.getInstance(getContext()).movieDAO().getList();
        favoriteAdapter = new FavoriteAdapter(favoriteList, getContext());
        favoriteAdapter.setOnclickFavorite(new CallBackItem() {
            @Override
            public void onClickItem(int positon, String id) {
                favoriteList.remove(positon);
                MovieDataBase.getInstance(getContext()).movieDAO().delete(Integer.parseInt(id));
                favoriteAdapter.notifyDataSetChanged();
            }
        });
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.notifyDataSetChanged();
        favoriteAdapter.setOnclicktype(new CallBackUrl() {
            @Override
            public void type(int type, String id) {
                if (type == 1) {
                    startActivity(new Intent(getContext(), DetailMovieActivity.class).putExtra("KEY_ID", id));
                } else {
                    startActivity(new Intent(getContext(), TvShowDetailActivity.class).putExtra("TV_SHOW", id));
                }
            }
        });
    }

    private void initUI(View view) {
        recyclerView = view.findViewById(R.id.recylerview_favorite);
    }


}