package com.marko.teletrader.repository.news;

import com.marko.teletrader.model.news.NewsResult;
import com.marko.teletrader.repository.BaseRepository;

import java.io.IOException;

import retrofit2.Call;

public class NewsRepository{

    public static NewsResult fetchResponse() {
        Call<NewsResult> call = BaseRepository.getXMLService(NewsService.class).getNewsList();

        try{
            return call.execute().body();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
