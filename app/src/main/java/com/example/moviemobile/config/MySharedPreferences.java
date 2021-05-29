package com.example.moviemobile.config;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String MY_SHARED_FREFERENCES = "MY_SHARED_FREFERENCES";
    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_FREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void puBoleanValue(String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_FREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_FREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public boolean getBoleanValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_FREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }
}
