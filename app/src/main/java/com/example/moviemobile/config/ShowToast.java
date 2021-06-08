package com.example.moviemobile.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviemobile.R;

public class ShowToast {
    public Context context;
    public static final String REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public ShowToast(Context context) {
        this.context = context;
    }

    public static void showToast(String text,Context context) {
        Toast toast = new Toast(context);
        TextView textView2;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_toast, null);
        textView2 = view.findViewById(R.id.showText);
        textView2.setText(text);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
