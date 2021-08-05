package com.marko.teletrader.repository.news;

import com.marko.teletrader.model.news.NewsResult;
import com.marko.teletrader.model.symbol.SymbolResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsService{

    @GET("tt_news_list.xml")
    Call<NewsResult> getNewsList();

}
