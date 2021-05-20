package com.example.moviemobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.moviemobile.R;
import com.example.moviemobile.model.PhotoSlider;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PhotoSliderAdapter extends PagerAdapter {
    private Context context;

    public PhotoSliderAdapter(Context context, List<PhotoSlider> list) {
        this.context = context;
        this.list = list;
    }

    private List<PhotoSlider> list;

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }


    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_slider, container, false);
        ImageView imageView = view.findViewById(R.id.image_slider);
        PhotoSlider slider = list.get(position);
        if (slider != null) {
            Glide.with(context).load(slider.getIdPhoto()).into(imageView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }
}
