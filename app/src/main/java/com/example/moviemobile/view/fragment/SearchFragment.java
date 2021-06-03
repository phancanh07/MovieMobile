package com.example.moviemobile.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.moviemobile.R;
import com.example.moviemobile.adapter.MovieListAdapter;
import com.example.moviemobile.adapter.SearchDataAdapter;
import com.example.moviemobile.adapter.TVshowTopAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.config.ShowToast;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.movie.Example;
import com.example.moviemobile.model.movie.Result;
import com.example.moviemobile.model.search.KnownFor;
import com.example.moviemobile.model.search.ResultSearch;
import com.example.moviemobile.model.search.SearchData;
import com.example.moviemobile.model.tvshow.TvTop;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment implements View.OnClickListener, CallBackItem {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btn_search;
    private List<Result> resultList = new ArrayList<>();
    private List<com.example.moviemobile.model.tvshow.Result> listtv = new ArrayList<>();
    private List<ResultSearch> peoPleList = new ArrayList<>();
    private String mParam1;
    private String mParam2;
    private EditText editText;
    private TVshowTopAdapter tVshowTopAdapter;
    private Spinner spinner;
    private TextInputLayout textSearch;
    private RecyclerView recylerView_movie_search;
    private MovieListAdapter movieListAdapter;
    private IfMovieList ifMovieList;
    private SearchDataAdapter searchDataAdapter;

    public SearchFragment() {

    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ifMovieList = ApiRetrofit.getClient().create(IfMovieList.class);
        initUI(view);
        btn_search.setOnClickListener(this::onClick);
        return view;
    }

    private void initUI(View view) {
        spinner = view.findViewById(R.id.planets_spinner);
        textSearch = view.findViewById(R.id.txt_search);
        btn_search = view.findViewById(R.id.search_movie);
        recylerView_movie_search = view.findViewById(R.id.recylerView_movie_search);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    private void getMovie(String idSearch) {

        ifMovieList.getSearchMovie(idSearch).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful()) {
                    ShowToast.showToast("Successful Search", getContext());
                    resultList.clear();
                    resultList.addAll(response.body().getResults());
                    movieListAdapter = new MovieListAdapter(resultList, getContext(), SearchFragment.this::onClickItem);
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
                    recylerView_movie_search.setLayoutManager(manager);
                    recylerView_movie_search.setAdapter(movieListAdapter);
                }

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

    private void getPeople(String id) {
        ifMovieList.getSearchPeople(id).enqueue(new Callback<SearchData>() {
            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                if (response.isSuccessful()) {
                    ShowToast.showToast("Successful Search", getContext());
                    peoPleList.clear();
                    peoPleList.addAll(response.body().getResults());
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
                    searchDataAdapter = new SearchDataAdapter(getContext(), peoPleList, SearchFragment.this::onClickItem);
                    recylerView_movie_search.setLayoutManager(manager);
                    recylerView_movie_search.setAdapter(searchDataAdapter);
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {

            }
        });
    }

    private void getSearchTV(String id) {
        ifMovieList.getSearchTV(id).enqueue(new Callback<TvTop>() {
            @Override
            public void onResponse(Call<TvTop> call, Response<TvTop> response) {
                if (response.isSuccessful()){
                    ShowToast.showToast("Successful Search", getContext());
                    listtv.clear();
                    listtv.addAll(response.body().getResults());
                    tVshowTopAdapter = new TVshowTopAdapter(listtv, getContext(), SearchFragment.this::onClickItem);
                    StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
                    recylerView_movie_search.setLayoutManager(manager);
                    recylerView_movie_search.setAdapter(tVshowTopAdapter);
                }

            }

            @Override
            public void onFailure(Call<TvTop> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (spinner.getSelectedItemPosition() == 0) {
            getPeople(textSearch.getEditText().getText().toString());

        } else if (spinner.getSelectedItemPosition() == 1) {
            getMovie(textSearch.getEditText().getText().toString());

        } else if (spinner.getSelectedItemPosition() == 2) {
            getSearchTV(textSearch.getEditText().getText().toString());

        }
        ShowToast.showToast("Not Found", getContext());
    }

    @Override
    public void onClickItem(int positon, String id) {

    }
}