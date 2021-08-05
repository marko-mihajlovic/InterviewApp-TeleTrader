package com.marko.teletrader.repository.symbol;

import com.marko.teletrader.model.symbol.SymbolResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SymbolService{

    @GET("tt_symbol_list.xml")
    Call<SymbolResult> getSymbolList();

}
