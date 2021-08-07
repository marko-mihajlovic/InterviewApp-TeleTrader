package com.marko.teletrader.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.marko.teletrader.AppUtil;
import com.marko.teletrader.R;
import com.marko.teletrader.databinding.RowSymbolBinding;
import com.marko.teletrader.model.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SuppressLint("NotifyDataSetChanged")
public class SymbolAdapter extends RecyclerView.Adapter<SymbolAdapter.RowSymbol>{

    public static final int FORMAT_CHANGE_LAST = 1, FORMAT_BID_ASK = 2;
    public static final String formatModeKey = "formatMode";

    private final Context context;
    private List<Symbol> symbolList;
    private int formatMode;
    private final Handler handler;

    SharedPreferences userPref;

    public SymbolAdapter(Context context){
        this.context = context;
        this.symbolList = new ArrayList<>();
        this.handler = new Handler();

        userPref = context.getSharedPreferences("settings", MODE_PRIVATE);
        formatMode = userPref.getInt(formatModeKey, FORMAT_CHANGE_LAST);
    }

    public void setSymbolList(List<Symbol> symbolList){
        if(symbolList == null)
            symbolList = new ArrayList<>();

        this.symbolList = symbolList;
        notifyDataSetChanged();

        autoRefresh();
    }

    public void setFormatMode(int formatMode){
        if(this.formatMode == formatMode)
            return;

        this.formatMode = formatMode;
        userPref.edit().putInt(formatModeKey, formatMode).apply();

        notifyDataSetChanged();
    }


    @Override
    public int getItemCount(){
        return symbolList.size();
    }

    public static class RowSymbol extends RecyclerView.ViewHolder{
        RowSymbolBinding rowSymbolBinding;
        public RowSymbol(RowSymbolBinding rowSymbolBinding){
            super(rowSymbolBinding.getRoot());
            this.rowSymbolBinding = rowSymbolBinding;
        }
    }

    @NonNull
    @Override
    public RowSymbol onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        RowSymbolBinding rowSymbolBinding = RowSymbolBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new RowSymbol(rowSymbolBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RowSymbol rowSymbol, int position){

        RowSymbolBinding binding = rowSymbol.rowSymbolBinding;
        Symbol symbol = symbolList.get(position);

        binding.nameTxt.setText(symbol.getName());
        binding.changePercentTxt.setText(symbol.getChangePercentString());
        binding.lastTxt.setText(symbol.getLast());
        binding.txtSymbolBid.setText(symbol.getBid());
        binding.txtSymbolAsk.setText(symbol.getAsk());
        binding.txtSymbolHigh.setText(symbol.getHigh());
        binding.txtSymbolLow.setText(symbol.getLow());

        confTextColor(binding, symbol);
        confBgColor(binding, symbol);
        confFormat(binding);

        binding.getRoot().setOnClickListener(view -> AppUtil.showToast(context, symbol.toString()));

        binding.getRoot().setOnLongClickListener(view -> {
            askToRemoveItem(symbol);
            return true;
        });

    }

    private void confBgColor(RowSymbolBinding binding, Symbol symbol){
       switch(symbol.coloredBg){
           case DEFAULT:
           default:
               binding.lastTxt.setBackgroundColor(0);
               break;
           case RED:
               binding.lastTxt.setBackgroundColor(context.getColor(R.color.redLight));
               break;
           case GREEN:
               binding.lastTxt.setBackgroundColor(context.getColor(R.color.greenLight));
               break;
       }
    }

    private void confTextColor(RowSymbolBinding binding, Symbol symbol){
        if (symbol.getChangePercent() > 0) {
            binding.changePercentTxt.setTextColor(context.getColor(R.color.green));
        } else if (symbol.getChangePercent() < 0) {
            binding.changePercentTxt.setTextColor(context.getColor(R.color.red));
        } else {
            binding.changePercentTxt.setTextColor(context.getColor(R.color.defaultTextColor));
        }
    }

    private void confFormat(RowSymbolBinding binding){
        switch(formatMode){
            case FORMAT_CHANGE_LAST:
            default:
                binding.viewFormatChangeLast.setVisibility(View.VISIBLE);
                binding.viewFormatBidAsk.setVisibility(View.GONE);
                break;
            case FORMAT_BID_ASK:
                binding.viewFormatChangeLast.setVisibility(View.GONE);
                binding.viewFormatBidAsk.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void askToRemoveItem(Symbol symbol){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Remove item?");
        builder.setMessage(symbol.getName());
        
        builder.setPositiveButton(R.string.removeTxt, (dialog, id) -> {
            if(onRemoveItemListener !=null)
                onRemoveItemListener.onRemoveItem(symbol.getId());
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> dialog.cancel());
        builder.create().show();
    }

    /** Listen if user wants to remove item **/
    public OnRemoveItemListener onRemoveItemListener;

    public void setOnRemoveItemListener(OnRemoveItemListener onRemoveItemListener){
        this.onRemoveItemListener = onRemoveItemListener;
    }

    public interface OnRemoveItemListener{ void onRemoveItem(String id); }


    /** Auto Refresh **/
    private void autoRefresh(){
        cancelAutoRefresh();

        int i = 0;
        for(Symbol symbol : symbolList){
            if(symbol.coloredBg!= Symbol.BG_COLOR.DEFAULT)
                resetBg(symbol, i);

            autoRefreshPercent(symbol, i);
            autoRefreshLast(symbol, i);
            i++;
        }
    }

    private final static int lowMillis = 3000, highMillis = 10000, resetMillis =2000;
    private void autoRefreshPercent(Symbol symbol, int position){
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                symbol.changePercent = AppUtil.getRandomValue(symbol.changePercent);
                notifyItemChanged(position, null);

                handler.postDelayed(this, AppUtil.getRandomMillis(lowMillis, highMillis));
            }
        }, AppUtil.getRandomMillis(lowMillis, highMillis));
    }

    private void autoRefreshLast(Symbol symbol, int position){
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                double beforeChangeValue = symbol.last;
                AppUtil.logMsg("beforeChangeValue: " + beforeChangeValue);

                symbol.last = AppUtil.getRandomValue(symbol.last);
                symbol.coloredBg = symbol.last - beforeChangeValue >= 0 ? Symbol.BG_COLOR.GREEN : Symbol.BG_COLOR.RED;

                notifyItemChanged(position, null);

                handler.postDelayed(() -> resetBg(symbol, position), resetMillis);
                handler.postDelayed(this, AppUtil.getRandomMillis(lowMillis, highMillis));
            }
        }, AppUtil.getRandomMillis(lowMillis, highMillis));
    }

    private void cancelAutoRefresh(){
        handler.removeCallbacksAndMessages(null);
    }

    private void resetBg(Symbol symbol, int position){
        symbol.coloredBg = Symbol.BG_COLOR.DEFAULT;
        notifyItemChanged(position, null);
    }
}

