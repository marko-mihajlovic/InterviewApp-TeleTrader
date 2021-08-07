package com.marko.teletrader.ui.symbol;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marko.teletrader.model.symbol.Symbol;
import com.marko.teletrader.model.symbol.SymbolResult;
import com.marko.teletrader.repository.symbol.SymbolRepository;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SymbolViewModel extends ViewModel{

    private MutableLiveData<List<Symbol>> symbolList;
    private MutableLiveData<String> msg;

    public MutableLiveData<String> getMsg(){
        if(msg==null)
            msg = new MutableLiveData<>();

       return msg;
    }

    public MutableLiveData<List<Symbol>> getSymbolList(){
        if(symbolList==null){
            symbolList = new MutableLiveData<>();
            loadSymbol();
        }

        return symbolList;
    }

    public void loadSymbol(){
        SymbolRepository.makeCall().enqueue(new Callback<SymbolResult>(){
            @Override
            public void onResponse(@NonNull Call<SymbolResult> call, @NonNull Response<SymbolResult> response){
                if(response.isSuccessful() && response.body() != null){
                    List<Symbol> list = response.body().getSymbolList();
                    defSort(list);
                    symbolList.postValue(list);
                }else{
                    symbolList.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SymbolResult> call, @NonNull Throwable t){
                symbolList.postValue(null);
            }
        });
    }

    public void removeWithId(String id){
        if(symbolList==null || symbolList.getValue()==null)
            return;

        List<Symbol> list = symbolList.getValue();

        int pos = getItemPos(list, id);
        if(pos>=0) {
            list.remove(pos);
            symbolList.postValue(list);
            msg.setValue("Item deleted!");
            msg.setValue(null);
        }
    }

    private int getItemPos(List<Symbol> list, String id){
        if(symbolList==null || symbolList.getValue()==null)
            return -1;

        int i = 0;
        for(Symbol symbol : list){
            if(id.equals(symbol.getId()))
               return i;

            i++;
        }


        return -1;
    }


    public void sortAscending() {
        if(symbolList==null || symbolList.getValue()==null)
            return;

        List<Symbol> symbols = symbolList.getValue();
        Collections.sort(symbols, (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        symbolList.postValue(symbols);
    }

    public void sortDescending() {
        if(symbolList==null || symbolList.getValue()==null)
            return;

        List<Symbol> symbols = symbolList.getValue();
        Collections.sort(symbols, (o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName()));
        symbolList.postValue(symbols);
    }

    public void sortDefault() {
        if(symbolList==null || symbolList.getValue()==null)
            return;

        List<Symbol> symbols = symbolList.getValue();
        defSort(symbols);
        symbolList.postValue(symbols);
    }

    private void defSort(List<Symbol> symbols){
        Collections.sort(symbols, (o1, o2) -> o2.getId().compareToIgnoreCase(o1.getId()));
    }

}