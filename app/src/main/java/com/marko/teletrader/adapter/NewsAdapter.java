package com.marko.teletrader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marko.teletrader.databinding.RowNewsBinding;
import com.marko.teletrader.model.news.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.RowNews>{

    private Context context;
    private List<News> newsList;

    public NewsAdapter(Context context){
        this.context = context;
        this.newsList = new ArrayList<>();
    }

    public void setNewsList(List<News> newsList){
        if(newsList==null)
            newsList = new ArrayList<>();

        this.newsList = newsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RowNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        RowNewsBinding rowNewsBinding = RowNewsBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new RowNews(rowNewsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RowNews rowNews, int position){

        RowNewsBinding binding = rowNews.rowNewsBinding;
        News news = newsList.get(position);

        Glide.with(context)
                .load(news.getImageFullPath())
                .thumbnail(0.1f)
                .into(binding.imgView);

        binding.txtView.setText(news.getHeadline());
    }


    @Override
    public int getItemCount(){
        return newsList.size();
    }

    public static class RowNews extends RecyclerView.ViewHolder{
        RowNewsBinding rowNewsBinding;
        public RowNews(RowNewsBinding rowNewsBinding){
            super(rowNewsBinding.getRoot());
            this.rowNewsBinding = rowNewsBinding;
        }
    }

}
