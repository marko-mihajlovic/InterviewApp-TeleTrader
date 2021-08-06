package com.marko.teletrader.repository.news;

import com.marko.teletrader.model.news.NewsResult;
import com.marko.teletrader.repository.BaseRepository;

import retrofit2.Call;

public class NewsRepository{

    public static Call<NewsResult> makeCall() {
        return BaseRepository.getXMLService(NewsService.class).getNewsResult();
    }

}
