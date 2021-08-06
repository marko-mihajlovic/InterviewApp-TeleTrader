package com.marko.teletrader.ui.news;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marko.teletrader.adapter.NewsAdapter;
import com.marko.teletrader.databinding.NewsFragmentBinding;
import com.marko.teletrader.model.news.News;

import java.util.List;

public class NewsFragment extends Fragment{

    private NewsFragmentBinding binding;
    private NewsAdapter newsAdapter;

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = NewsFragmentBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        newsAdapter = new NewsAdapter(getActivity());
        binding.getRoot().setAdapter(newsAdapter);

        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getNews().observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(List<News> newsList){
        newsAdapter.setNewsList(newsList);
    }
}