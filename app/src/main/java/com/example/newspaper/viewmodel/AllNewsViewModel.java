package com.example.newspaper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newspaper.model.News;
import com.example.newspaper.model.NewsRepository;

import java.util.List;

public class AllNewsViewModel extends AndroidViewModel {
    private NewsRepository newsRepository;

    public AllNewsViewModel(@NonNull Application application, NewsRepository newsRepository) {
        super(application);
        this.newsRepository = newsRepository;
    }
    //this method will be used to show all news on home screen
    public LiveData<List<News>> getNewsByCategory(String category){
        return newsRepository.getMutableLiveData(category);
    }

    //Room db methods
    public void insertNews(News news){
        newsRepository.addNewsToRoom(news);
    }
    public void deleteNews(News news){
        newsRepository.removeNewsFromRoom(news);
    }
    public LiveData<List<News>> getNewsFromRoomDB(){
        return newsRepository.getNewsFromRoomDB();
    }
}
