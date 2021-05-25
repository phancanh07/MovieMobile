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

import com.example.moviemobile.model.character.CharacterTV;
import com.example.moviemobile.model.tv.Cast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CharacterTvAdapter extends RecyclerView.Adapter<CharacterTvAdapter.RecyclerViewHolder> {
    List<Cast> castList;
    Context context;

    public CharacterTvAdapter(List<Cast> castList, Context context) {
        this.castList = castList;
        this.context = context;
    }

    @Override
    public CharacterTvAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_item_character, parent, false);
        return new CharacterTvAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CharacterTvAdapter.RecyclerViewHolder holder, int position) {
        holder.tenNV.setText(castList.get(position).getRoles().get(0).getCharacter());
        holder.tenDV.setText(castList.get(position).getOriginalName());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "tama2.ttf");
        holder.tenDV.setTypeface(typeface);
        holder.tenNV.setTypeface(typeface);
        if (castList.get(position).getProfilePath() != null) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + castList.get(position).getProfilePath()).into(holder.imageView);
        } else {
            Glide.with(context).load(R.drawable.avatar).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (castList!=null){
            return castList.size();
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

                }
            });
        }
    }
}
