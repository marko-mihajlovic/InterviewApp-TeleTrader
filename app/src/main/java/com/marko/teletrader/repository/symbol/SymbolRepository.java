package com.marko.teletrader.repository.symbol;

import com.marko.teletrader.model.symbol.SymbolResult;
import com.marko.teletrader.repository.BaseRepository;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SymbolRepository{

    public static Call<SymbolResult> makeCall(){
        return BaseRepository.getXMLService(SymbolService.class).getSymbolResult();
    }

}
