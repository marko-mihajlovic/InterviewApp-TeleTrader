package com.marko.teletrader.model.symbol;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "Symbol")
public class Symbol {

    @Attribute public String id;
    @Attribute public String name;
    @Attribute public String tickerSymbol;
    @Attribute public String isin;
    @Attribute public String currency;
    @Attribute public String stockExchangeName;
    @Attribute public String decorativeName;

    @Path("Quote") @Attribute public String dateTime;
    @Path("Quote") @Attribute public double last;
    @Path("Quote") @Attribute public double high;
    @Path("Quote") @Attribute public double low;
    @Path("Quote") @Attribute public double bid;
    @Path("Quote") @Attribute public double ask;
    @Path("Quote") @Attribute public double volume;
    @Path("Quote") @Attribute public double change;
    @Path("Quote") @Attribute public double changePercent;

    public Symbol(){}

    @Override
    public String toString(){
        return "Symbol{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", tickerSymbol='" + tickerSymbol + '\'' + ", isin='" + isin + '\'' + ", currency='" + currency + '\'' +
                ", stockExchangeName='" + stockExchangeName + '\'' + ", decorativeName='" + decorativeName + '\'' + ", dateTime='" + dateTime + '\'' + ", last=" + last + ", high=" + high + ", low=" +
                low + ", bid=" + bid + ", ask=" + ask + ", volume=" + volume + ", change=" + change + ", changePercent=" + changePercent + '}';
    }
}
