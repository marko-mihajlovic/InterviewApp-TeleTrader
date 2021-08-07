package com.marko.teletrader.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.marko.teletrader.R;
import com.marko.teletrader.adapter.NewsAdapter;
import com.marko.teletrader.databinding.NewsFragmentBinding;
import com.marko.teletrader.model.news.News;

import java.util.List;

public class NewsFragment extends Fragment{

    private NewsFragmentBinding binding;
    private NewsAdapter newsAdapter;
    private NewsViewModel newsViewModel;

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

        defaultUI();
        confListAndAdapter();
        confViewModel();

        binding.listContainer.setOnRefreshListener(newsViewModel::loadNews);
    }

    private void confViewModel(){
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getNewsList().observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void confListAndAdapter(){
        newsAdapter = new NewsAdapter(getActivity());
        binding.listView.setAdapter(newsAdapter);
    }

    private void defaultUI(){
        binding.msgTxt.setText(R.string.loadingTxt);
        toggleVisibleViews(false);
    }

    private void updateUI(List<News> newsList){
        newsAdapter.setNewsList(newsList);
        binding.listContainer.setRefreshing(false);

        if(newsList==null || newsList.isEmpty()){
            toggleVisibleViews(false);
            binding.msgTxt.setText(R.string.noItemsTxt);
        }else{
            toggleVisibleViews(true);
            binding.msgTxt.setText("");
        }
    }

    private void toggleVisibleViews(boolean isListVisible){
        binding.listContainer.setVisibility(isListVisible ? View.VISIBLE : View.GONE);
        binding.msgTxt.setVisibility(isListVisible ? View.GONE : View.VISIBLE);
    }


}