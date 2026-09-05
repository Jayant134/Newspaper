package com.example.newspaper.model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newspaper.serviceapi.NewsApiService;
import com.example.newspaper.serviceapi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private ArrayList<News> newsArrayList = new ArrayList<>();
    private MutableLiveData<List<News>> mutableLiveData = new MutableLiveData<>();
    private Application application;
    public final NewsDAO newsDAO;
    Executor executor;
    Handler handler;

    public NewsRepository(Application application) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(application);
        this.newsDAO = newsDatabase.getNewsDao();
        this.application = application;
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    //this method is used to call the api request and store response in newsArrayList. - of Retrofit
    public MutableLiveData<List<News>> getMutableLiveData(String category){
        NewsApiService newsApiService = RetrofitInstance.getService();
        Call<Result> call = newsApiService.getNews("3i-YC_nEtlkF7Ofo-AtnoCjkWixHKuZI7VIu_irOgrhyif9E", category);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Result result = response.body();
                if(result != null && result.getNews()!= null){
                    newsArrayList = (ArrayList<News>) result.getNews();
                    mutableLiveData.setValue(newsArrayList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Result> call, @NonNull Throwable t) {

            }
        });
        return mutableLiveData;
    }

    //Methods for Room Database
    public void addNewsToRoom(News news){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                newsDAO.insert(news);
            }
        });
    }

    public void removeNewsFromRoom(News news){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                newsDAO.delete(news);
            }
        });
    }

    public LiveData<List<News>> getNewsFromRoomDB(){
        return newsDAO.getNewsFromRoomDB();
    }
}
