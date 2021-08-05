package com.marko.teletrader.model.symbol;


import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml(name = "Result")
public class SymbolResult{

    @Path("SymbolList")
    @Element
    public List<Symbol> symbolList;

    public SymbolResult(){ }

    public List<Symbol> getSymbolList(){
        return symbolList;
    }

}
