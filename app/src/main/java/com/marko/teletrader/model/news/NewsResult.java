package com.marko.teletrader.model.news;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "Result")
public class NewsResult{

    @Path("NewsList")
    @Element
    public List<News> newsList;

    public NewsResult(){ }

    public List<News> getNewsList(){
        return newsList;
    }
}
