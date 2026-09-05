package com.example.newspaper.clickHandlers;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.newspaper.R;
import com.example.newspaper.model.News;

public class NewsItemClickHandler {
    Context context;

    public NewsItemClickHandler(Context context) {
        this.context = context;
    }

    public void onNewsItemClicked(View view, News news){
        Bundle bundle = new Bundle();
        bundle.putString("url", news.getUrl());
        newsDetails detailsFragment = new newsDetails();    //just created the object of fragment. loading will be done by loadFragment. like creating intent and calling startActivity(i).
        detailsFragment.setArguments(bundle);
        loadDetailsFragment(detailsFragment);
    }

    public void loadDetailsFragment(Fragment fragment){
        FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
