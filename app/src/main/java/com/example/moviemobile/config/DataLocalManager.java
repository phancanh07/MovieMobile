package com.example.moviemobile.config;

import android.content.Context;

import com.example.moviemobile.model.acount.Acount;
import com.google.gson.Gson;

public class DataLocalManager {
    private static final String PREFERENCES = "PREFERENCES";
    private static final String CHECKED = "CHECKED";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);

    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setUser(Acount user) {
        Gson gson = new Gson();
        String jsonUser = gson.toJson(user);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREFERENCES, jsonUser);
    }

    public static Acount getAcount() {
        String jsonUser = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREFERENCES);
        Gson gson = new Gson();
        Acount acount = gson.fromJson(jsonUser, Acount.class);
        return acount;
    }

    public static void setChecked(boolean check) {
        DataLocalManager.getInstance().mySharedPreferences.puBoleanValue(CHECKED, check);
    }

    public static boolean getChecked() {
        return DataLocalManager.getInstance().mySharedPreferences.getBoleanValue(CHECKED);
    }
}
