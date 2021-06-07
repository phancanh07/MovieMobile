package com.example.moviemobile.roomdata;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviemobile.model.Favorite;

@Database(entities = {Favorite.class}, version = 1)
public abstract class MovieDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "moviemobile.db";
    private static MovieDataBase instance;

    public static synchronized MovieDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract MovieDAO movieDAO();

}
