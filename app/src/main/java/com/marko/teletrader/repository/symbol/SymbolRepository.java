package com.marko.teletrader.repository.symbol;

import com.marko.teletrader.model.symbol.SymbolResult;
import com.marko.teletrader.repository.BaseRepository;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SymbolRepository{

    public static SymbolResult fetchResponse() {
        Call<SymbolResult> call = BaseRepository.getXMLService(SymbolService.class).getSymbolList();

        Response<SymbolResult> response;
        try{
            response = call.execute();
            return response.body();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
