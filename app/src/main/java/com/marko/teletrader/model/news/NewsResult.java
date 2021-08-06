package com.marko.teletrader.model.news;

import androidx.annotation.NonNull;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.ArrayList;
import java.util.List;

@Xml(name = "Result")
public class NewsResult{

    @Path("NewsList")
    @Element
    public List<News> newsList;

    public NewsResult(){ }

    @NonNull
    public List<News> getNewsList(){
        if(newsList==null)
            return new ArrayList<>();

        return newsList;
    }

}
