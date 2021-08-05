package com.marko.teletrader.model.news;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "NewsArticle")
public class News{

    @Attribute public String id;
    @Attribute public String author;
    @Attribute public String dateTime;

    @PropertyElement(name = "Headline", writeAsCData = true)
    public String headline;

    @Path("Tags/Tag/PicTT")
    @PropertyElement(name = "ImageID")
    public String imageId;


    public News(){}

    @Override
    public String toString(){
        return "News{" + "id='" + id + '\'' + ", author='" + author + '\'' + ", dateTime='" + dateTime + '\'' + ", headline='" + headline + '\'' + ", imageId='" + imageId + '\'' + '}';
    }
}
