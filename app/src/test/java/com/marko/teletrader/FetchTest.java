package com.marko.teletrader;

import org.junit.Test;

import static org.junit.Assert.*;

import com.marko.teletrader.model.news.News;
import com.marko.teletrader.model.news.NewsResult;
import com.marko.teletrader.model.symbol.Symbol;
import com.marko.teletrader.model.symbol.SymbolResult;
import com.marko.teletrader.repository.news.NewsRepository;
import com.marko.teletrader.repository.symbol.SymbolRepository;

public class FetchTest{

    @Test
    public void testFetchSymbol(){
        SymbolResult symbolResult = SymbolRepository.fetchResponse();

        assertNotNull(symbolResult);

        for (Symbol symbol : symbolResult.getSymbolList()){
            System.out.println("testFetch_symbol: " + symbol.toString());
        }
    }

    @Test
    public void testFetchNews(){
        NewsResult newsResult = NewsRepository.fetchResponse();

        assertNotNull(newsResult);

        for (News news : newsResult.getNewsList()){
            System.out.println("testFetch_news: " + news.toString());
        }
    }
}