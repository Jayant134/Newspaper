package com.example.newspaper.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDAO {
    @Insert
    public void insert(News news);
    @Delete
    public void delete(News news);
    @Query("SELECT * FROM news_table")
    LiveData<List<News>> getNewsFromRoomDB();
}
