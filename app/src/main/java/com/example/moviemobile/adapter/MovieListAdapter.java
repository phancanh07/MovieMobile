package com.example.moviemobile.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.model.movie.Result;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.RecyclerViewHolder> {
    private List<Result> resultList;
    private Context context;
    private CallBackItem callBackItem;

    public MovieListAdapter(List<Result> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    public MovieListAdapter(List<Result> resultList, Context context, CallBackItem callBackItem) {
        this.resultList = resultList;
        this.context = context;
        this.callBackItem = callBackItem;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_adapter, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        Result result = resultList.get(position);
        holder.textView.append(result.getTitle());

        holder.vote_average.setText(result.getVoteAverage() + "");
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + result.getPosterPath()).into(holder.imageView);

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
        private TextView textView, vote_average;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_trend_name_movie);
            imageView = itemView.findViewById(R.id.img_trend_movie);
            vote_average = itemView.findViewById(R.id.txt_vote_average);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackItem.onClickItem(getAbsoluteAdapterPosition(), String.valueOf(resultList.get(getAbsoluteAdapterPosition()).getId()));
                }
            });
        }
    }
}
