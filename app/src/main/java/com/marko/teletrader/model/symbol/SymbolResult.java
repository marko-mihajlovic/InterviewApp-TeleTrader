package com.marko.teletrader.model.symbol;


import androidx.annotation.NonNull;

import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.ArrayList;
import java.util.List;

@Xml(name = "Result")
public class SymbolResult{

    @Path("SymbolList")
    @Element
    public List<Symbol> symbolList;

    public SymbolResult(){ }

    @NonNull
    public List<Symbol> getSymbolList(){
        if(symbolList==null)
            return new ArrayList<>();
        
        return symbolList;
    }

}
