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

import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.model.character.DetailCharacter;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class DetailCharacterAdapter extends RecyclerView.Adapter<DetailCharacterAdapter.RecyclerViewHolder> {
    private List<DetailCharacter> list;
    private Context context;
    private static final int NAM = 2;
    private static final int NU = 1;

    public DetailCharacterAdapter(List<DetailCharacter> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public DetailCharacterAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_character_detail, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DetailCharacterAdapter.RecyclerViewHolder holder, int position) {
        holder.tieusu.setText(list.get(position).getBiography());
        holder.tenDV.setText(list.get(position).getName());
        holder.place_of_birth.setText("Place of birth:\t" + list.get(position).getPlaceOfBirth());
        if (list.get(position).getGender() == NAM) {
            holder.gender.setText("Gender :Male");
        } else {
            holder.gender.setText("Gender :Female");
        }
        if (list.get(position).getProfilePath() != null) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + list.get(position).getProfilePath()).into(holder.imageView);
        } else {
            Glide.with(context).load(R.drawable.avatar).into(holder.imageView);
        }
        holder.birdday.setText("Birth Day:" + list.get(position).getBirthday());
        if (list.get(position).getDeathday() == null) {
            holder.deatday.setText("Death Day : NaN");
        } else {
            holder.deatday.setText("Death Day :" + list.get(position).getDeathday());
        }
        holder.phobien.setText("Popularity:\t" + list.get(position).getPopularity());
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "tama2.ttf");
        holder.place_of_birth.setTypeface(typeface);
        holder.birdday.setTypeface(typeface);
        holder.tieusu.setTypeface(typeface);
        holder.tenDV.setTypeface(typeface);
        holder.deatday.setTypeface(typeface);
        holder.phobien.setTypeface(typeface);
        holder.gender.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tenDV, birdday, deatday, gender, place_of_birth, phobien;
        private ImageView imageView;
        ReadMoreTextView tieusu;

        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tenDV = itemView.findViewById(R.id.name_character_detail);
            birdday = itemView.findViewById(R.id.birday);
            deatday = itemView.findViewById(R.id.deatday);
            tieusu = itemView.findViewById(R.id.biography);
            gender = itemView.findViewById(R.id.gender);
            place_of_birth = itemView.findViewById(R.id.place_day);
            imageView = itemView.findViewById(R.id.avatar);
            phobien = itemView.findViewById(R.id.phobien);
        }
    }
}
