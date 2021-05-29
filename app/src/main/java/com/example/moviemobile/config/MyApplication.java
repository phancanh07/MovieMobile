package com.example.moviemobile.config;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
        ApiRetrofit.init(getApplicationContext());
    }
}
