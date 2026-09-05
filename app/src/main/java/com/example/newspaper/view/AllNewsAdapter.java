package com.example.newspaper.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.R;
import com.example.newspaper.clickHandlers.NewsItemClickHandler;
import com.example.newspaper.databinding.NewsItemBinding;
import com.example.newspaper.model.News;

import java.util.ArrayList;

public class AllNewsAdapter extends RecyclerView.Adapter<AllNewsAdapter.NewsViewHolder> {
    Context context;
    private ArrayList<News> newsArrayList;

    public AllNewsAdapter(Context context, ArrayList<News> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.news_item,
                parent,
                false
        );
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = newsArrayList.get(position);
        holder.newsItemBinding.setNews(news);
        holder.newsItemBinding.setClickHandler(new NewsItemClickHandler(context));
    }

    @Override
    public int getItemCount() {
        return (!newsArrayList.isEmpty()) ? newsArrayList.size() : 0;
    }

    //for room db
    public void setNewsArrayList(ArrayList<News> newsArrayList){
        this.newsArrayList = newsArrayList;
        notifyDataSetChanged();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        private NewsItemBinding newsItemBinding;

        public NewsViewHolder(NewsItemBinding newsItemBinding) {
            super(newsItemBinding.getRoot());
            this.newsItemBinding = newsItemBinding;
//            newsItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //logic for on clicking news item
//                }
//            });
        }
    }
}
