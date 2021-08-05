package com.marko.teletrader.repository;


import com.tickaroo.tikxml.TikXml;
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class BaseRepository{

    private static final String BASE_URL = "https://www.teletrader.rs/downloads/";

    private static OkHttpClient getOkHttp(){
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor("android_tt", "Sk3M!@p9e"))
                .build();
    }

    private static TikXmlConverterFactory getTikXml(){
        TikXml tikXml = new TikXml.Builder()
                .exceptionOnUnreadXml(false)
                .build();

        return TikXmlConverterFactory.create(tikXml);
    }

    public static <T> T getXMLService(Class<T> clsOfService){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(getTikXml())
                .client(getOkHttp())
                .build();

        return retrofit.create(clsOfService);
    }

}
