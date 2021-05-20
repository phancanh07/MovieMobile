package com.example.moviemobile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.model.movie.Result;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TopMovieRatingAdapter extends RecyclerView.Adapter<TopMovieRatingAdapter.RecyclerViewHolder> {

    private List<Result> resultList;
    private Context context;

    public TopMovieRatingAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @Override
    public TopMovieRatingAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_adapter, parent, false);
        return new TopMovieRatingAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TopMovieRatingAdapter.RecyclerViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.textView.setText(result.getTitle());
        holder.count_rating.setText(result.getVoteAverage() + "");
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + result.getPosterPath()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NAME:", result.getOriginalTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (resultList != null) {
            return resultList.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView, count_rating;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_trend_name_movie);
            imageView = itemView.findViewById(R.id.img_trend_movie);
            count_rating = itemView.findViewById(R.id.txt_vote_average);

        }
    }
}
