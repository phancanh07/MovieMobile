package com.example.moviemobile.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import com.example.moviemobile.config.ShowToast;
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.controller.CallBackUrl;
import com.example.moviemobile.model.Favorite;
import com.example.moviemobile.roomdata.MovieDataBase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.RecyclerViewHolder> {
    private List<Favorite> favoriteList;
    private Context context;
    private CallBackItem callcaBackItem;
    private CallBackUrl calltype;

    public FavoriteAdapter(List<Favorite> favoriteList, Context context) {
        this.favoriteList = favoriteList;
        this.context = context;

    }

    public void setOnclickFavorite(CallBackItem onclickFavoriteCallBackItem) {
        this.callcaBackItem = onclickFavoriteCallBackItem;
        notifyDataSetChanged();
    }

    public void setOnclicktype(CallBackUrl calltype) {
        this.calltype = calltype;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_adapter, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        Favorite favorite = favoriteList.get(position);
        holder.textView.setText(favoriteList.get(position).getTitle());
        holder.vote_average.setText(favoriteList.get(position).getVoteAverage() + "");
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "tama2.ttf");
        holder.textView.setTypeface(typeface);
        holder.vote_average.setTypeface(typeface);
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + favoriteList.get(position).getUrl()).into(holder.imageView);

        if (MovieDataBase.getInstance(context).movieDAO().checkMovie(favoriteList.get(position).getIdMovie())) {
            holder.icon.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.icon.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                v.animate().setDuration(500).x(-v.getWidth()).alpha(0f);
                callcaBackItem.onClickItem(position, String.valueOf(favoriteList.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (favoriteList != null) {
            return favoriteList.size();
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
                    calltype.type(favoriteList.get(getAbsoluteAdapterPosition()).getType(), String.valueOf(favoriteList.get(getAbsoluteAdapterPosition()).getIdMovie()));
                }
            });
        }
    }
}