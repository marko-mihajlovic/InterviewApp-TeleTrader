package com.marko.teletrader.ui.news;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marko.teletrader.model.news.News;
import com.marko.teletrader.model.news.NewsResult;
import com.marko.teletrader.repository.news.NewsRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewModel extends ViewModel{

    private MutableLiveData<List<News>> newsList;

    public MutableLiveData<List<News>> getNews() {
        if (newsList == null) {
            newsList = new MutableLiveData<>();
            loadNews();
        }

        return newsList;
    }

    private void loadNews() {
        NewsRepository.makeCall().enqueue(new Callback<NewsResult>(){
            @Override
            public void onResponse(@NonNull Call<NewsResult> call, @NonNull Response<NewsResult> response){
                if(response.isSuccessful() && response.body() != null){
                    newsList.postValue(response.body().getNewsList());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NewsResult> call, @NonNull Throwable t){
                newsList.postValue(null);
            }
        });
    }
}