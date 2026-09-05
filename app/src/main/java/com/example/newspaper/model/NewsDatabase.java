package com.example.newspaper.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {News.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDAO getNewsDao();
    public static NewsDatabase dbInstance;
    public static synchronized NewsDatabase getInstance(Context context){
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    NewsDatabase.class,
                    "news_db"
            ).fallbackToDestructiveMigration()
                    .build();
        }
        return dbInstance;
    }
}
