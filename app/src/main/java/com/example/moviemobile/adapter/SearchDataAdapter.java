package com.example.moviemobile.adapter;

import android.content.Context;
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
import com.example.moviemobile.controller.CallBackItemCharacter;
import com.example.moviemobile.model.search.ResultSearch;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.RecyclerViewHolder> {
    private Context context;
    private List<ResultSearch> list;
    private CallBackItemCharacter itemCharacter;

    public SearchDataAdapter(Context context, List<ResultSearch> list, CallBackItemCharacter itemCharacter) {
        this.context = context;
        this.list = list;
        this.itemCharacter = itemCharacter;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item_character, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {
        holder.tenDV.setText(list.get(position).getName());
        holder.tenNV.setText("Popularity :\t" + list.get(position).getPopularity() + "");
        if (list.get(position).getProfilePath() != null) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + list.get(position).getProfilePath()).into(holder.imageView);
        }
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
        private TextView tenDV, tenNV;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_dienvien);
            tenDV = itemView.findViewById(R.id.ten_dien_vien);
            tenNV = itemView.findViewById(R.id.vai_dien_vien);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemCharacter.onClickItemCharacter(getAbsoluteAdapterPosition(), String.valueOf(list.get(getAbsoluteAdapterPosition()).getId()));
                }
            });

        }
    }
}
