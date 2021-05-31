package com.example.moviemobile.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.model.tvshow.Similar;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.RecyclerViewHolder> {
    Context context;
    List<Similar> list;
    private CallBackItem callBackItem;

    public SimilarAdapter(Context context, List<Similar> list, CallBackItem callBackItem) {
        this.context = context;
        this.list = list;
        this.callBackItem = callBackItem;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_adapter, parent, false);
        return new SimilarAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        Similar similar = list.get(position);
        holder.textView.setText("" + similar.getName());
        holder.count_rating.setText(similar.getVoteAverage() + "");
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + similar.getPosterPath()).into(holder.imageView);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "tama2.ttf");
        holder.textView.setTypeface(typeface);
        holder.count_rating.setTypeface(typeface);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackItem.onClickItem(getAbsoluteAdapterPosition(), String.valueOf(list.get(getAbsoluteAdapterPosition()).getId()));
                }
            });
        }
    }
}
