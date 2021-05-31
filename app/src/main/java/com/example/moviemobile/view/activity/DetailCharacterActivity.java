package com.example.moviemobile.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.moviemobile.R;
import com.example.moviemobile.adapter.DetailCharacterAdapter;
import com.example.moviemobile.config.ApiRetrofit;
import com.example.moviemobile.config.SendID;
import com.example.moviemobile.controller.IfMovieList;
import com.example.moviemobile.model.character.Characters;
import com.example.moviemobile.model.character.DetailCharacter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCharacterActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<DetailCharacter> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_character);
        getSupportActionBar().setTitle("Character");
        recyclerView = findViewById(R.id.recyler_view_detail);
        Intent intent = getIntent();
        String key = intent.getStringExtra("ID_CHARACTER");
        if (key != null) {
            getData(key);
        }
        getData(SendID.id);

    }

    private void getData(String id) {
        IfMovieList ifMovieList = ApiRetrofit.getClient().create(IfMovieList.class);
        ifMovieList.getCharactersDetail(id).enqueue(new Callback<DetailCharacter>() {
            @Override
            public void onResponse(Call<DetailCharacter> call, Response<DetailCharacter> response) {
                if (response.isSuccessful()) {
                    list.add(response.body());
                    LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
                    DetailCharacterAdapter adapter = new DetailCharacterAdapter(list, getApplicationContext());
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setHasFixedSize(false);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<DetailCharacter> call, Throwable t) {
                Log.d("sai", t.getMessage());
            }
        });
    }
}