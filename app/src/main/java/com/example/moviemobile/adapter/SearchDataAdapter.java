package com.example.moviemobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemobile.R;
import com.example.moviemobile.model.search.KnownFor;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchDataAdapter extends RecyclerView.Adapter<SearchDataAdapter.RecyclerViewHolder> {
    private Context context;
    private List<KnownFor> list;

    public SearchDataAdapter(Context context, List<KnownFor> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_adapter, parent, false);
        return new SearchDataAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public RecyclerViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
