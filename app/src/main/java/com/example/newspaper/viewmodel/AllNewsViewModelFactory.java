package com.example.newspaper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.newspaper.model.NewsRepository;

public class AllNewsViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private NewsRepository newsRepository;

    public AllNewsViewModelFactory(Application application, NewsRepository newsRepository) {
        this.application = application;
        this.newsRepository = newsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AllNewsViewModel.class)){
            return (T)new AllNewsViewModel(application, newsRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
