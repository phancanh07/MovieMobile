package com.example.moviemobile.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import com.example.moviemobile.config.ShowToast;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.model.Favorite;
import com.example.moviemobile.model.movie.Result;
import com.example.moviemobile.roomdata.MovieDataBase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.RecyclerViewHolder> {
    private List<Result> resultList;
    private Context context;
    private CallBackItem callBackItem;


    private List<Favorite> favorites  ;

    public MovieListAdapter(List<Result> resultList, Context context, CallBackItem callBackItem) {
        this.resultList = resultList;
        this.context = context;
        this.callBackItem = callBackItem;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.movie_list_adapter, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        Favorite favorite = new Favorite();
        holder.textView.setText(resultList.get(position).getOriginalTitle());
        holder.vote_average.setText(resultList.get(position).getVoteAverage() + "");
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "tama2.ttf");
        holder.textView.setTypeface(typeface);
        holder.vote_average.setTypeface(typeface);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + resultList.get(position).getPosterPath()).into(holder.imageView);
        if (MovieDataBase.getInstance(context).movieDAO().checkMovie(resultList.get(position).getId())) {
            holder.icon.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite.setIdMovie(resultList.get(position).getId());
                favorite.setTitle(resultList.get(position).getOriginalTitle());
                favorite.setVoteAverage(resultList.get(position).getVoteAverage());
                favorite.setUrl(resultList.get(position).getPosterPath());
                favorite.setType(1);
                if (MovieDataBase.getInstance(context).movieDAO().checkMovie(resultList.get(position).getId()) == false) {
                    MovieDataBase.getInstance(context).movieDAO().insertMovie(favorite);
                    ShowToast.showToast("đã thêm ", context);
                    notifyDataSetChanged();
                } else {
                    holder.icon.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    ShowToast.showToast("đã xóa ", context);
                    MovieDataBase.getInstance(context).movieDAO().delete(resultList.get(position).getId());
                    notifyDataSetChanged();
                }

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
        private ImageView imageView, icon;
        private TextView textView, vote_average;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txt_trend_name_movie);
            imageView = itemView.findViewById(R.id.img_trend_movie);
            vote_average = itemView.findViewById(R.id.txt_vote_average);
            icon = itemView.findViewById(R.id.iconfavorite);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBackItem.onClickItem(getAbsoluteAdapterPosition(), String.valueOf(resultList.get(getAbsoluteAdapterPosition()).getId()));
                }
            });
        }
    }


}
