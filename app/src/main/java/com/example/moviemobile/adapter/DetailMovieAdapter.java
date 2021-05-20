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
import com.example.moviemobile.model.detail.Detail;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DetailMovieAdapter extends RecyclerView.Adapter<DetailMovieAdapter.RecyclerViewHolder> {
    private List<Detail> detailList;
    private Context context;

    public DetailMovieAdapter(List<Detail> detailList, Context context) {
        this.detailList = detailList;
        this.context = context;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item_moviee, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        Glide.with(context).load("https://image.tmdb.org/t/p/w500" + detailList.get(position).getPosterPath()).into(holder.imageView);
        for (int i = 0; i < detailList.get(position).getGenres().size(); i++) {
            holder.txt_theloai.append(detailList.get(position).getGenres().get(i).getName() + ",");
        }
        for (int i = 0; i < detailList.get(position).getSpokenLanguages().size(); i++) {
            holder.txt_language.append(detailList.get(position).getSpokenLanguages().get(i).getEnglishName() + ",");
        }
        holder.name.setText(detailList.get(position).getOriginalTitle());
        holder.txt_day_movie.setText("Day:\t" + detailList.get(position).getReleaseDate());
        for (int i = 0; i < detailList.get(position).getProductionCountries().size(); i++) {
            holder.txt_country.append(detailList.get(position).getProductionCountries().get(i).getName());
        }

        holder.txt_overview.setText(detailList.get(position).getOverview());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "tama2.ttf");
        holder.txt_overview.setTypeface(typeface);
        holder.name.setTypeface(typeface);
        holder.txt_country.setTypeface(typeface);
        holder.txt_day_movie.setTypeface(typeface);
        holder.txt_language.setTypeface(typeface);
        holder.txt_theloai.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        if (detailList != null) {
            return detailList.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txt_overview, txt_day_movie, txt_country, txt_language, txt_theloai, name;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.postermovie);
            txt_country = itemView.findViewById(R.id.movie_country);
            txt_overview = itemView.findViewById(R.id.movie_overview);
            txt_day_movie = itemView.findViewById(R.id.day_movie);
            txt_language = itemView.findViewById(R.id.movie_language);
            txt_theloai = itemView.findViewById(R.id.theloai_movie);
            name = itemView.findViewById(R.id.name_movie);
        }
    }

}
