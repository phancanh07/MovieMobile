package com.example.moviemobile.adapter;

import android.content.Context;
import android.text.SpannableString;
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
import com.example.moviemobile.controller.CallBackItem;
import com.example.moviemobile.model.tvshow.DetailTVShow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TvshowDetailAdapter extends RecyclerView.Adapter<TvshowDetailAdapter.RecyclerViewHolder> {
    private List<DetailTVShow> resultList;
    private Context context;

    public TvshowDetailAdapter(List<DetailTVShow> resultList, Context context) {
        this.resultList = resultList;
        this.context = context;
    }

    @Override
    public TvshowDetailAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tv_detail_item, parent, false);
        return new TvshowDetailAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TvshowDetailAdapter.RecyclerViewHolder holder, int position) {
        DetailTVShow result = resultList.get(position);
        holder.name_tvshow.setText("Movie Name:\t" + result.getName());

        for (int i = 0; i < result.getGenres().size(); i++) {
            holder.theloai.append(result.getGenres().get(i).getName() + "");
        }

        for (int i = 0; i < result.getSpokenLanguages().size(); i++) {
            holder.ngonngu.append(result.getSpokenLanguages().get(i).getName() + "");
        }
        holder.last_day.setText("First Air Date :" + result.getFirstAirDate());
        for (int i = 0; i < result.getCreatedBy().size(); i++) {
            holder.created_by.append(result.getCreatedBy().get(position).getName() + ",");
        }
        for (int i = 0; i < result.getProductionCountries().size(); i++) {
            holder.country.append(result.getProductionCountries().get(i).getName() + ",");
        }

        holder.total_number.setText("Total Number:\t" + result.getNumberOfEpisodes());
        holder.total_season.setText("Total Season :\t" + result.getNumberOfSeasons());
        holder.tvshow_overview.setText(result.getOverview());


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
        private TextView name_tvshow, tvshow_overview, total_season, total_number, created_by, last_day, theloai, country, ngonngu;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name_tvshow = itemView.findViewById(R.id.name_tvshow);
            imageView = itemView.findViewById(R.id.postertvshow);
            country = itemView.findViewById(R.id.tvshow_country);
            ngonngu = itemView.findViewById(R.id.tv_language);
            theloai = itemView.findViewById(R.id.theloai_tvshow);
            total_season = itemView.findViewById(R.id.total_season);
            total_number = itemView.findViewById(R.id.total_episodes);
            created_by = itemView.findViewById(R.id.daodien);
            last_day = itemView.findViewById(R.id.last_day);
            tvshow_overview=itemView.findViewById(R.id.tvshow_overview);
        }
    }
}
