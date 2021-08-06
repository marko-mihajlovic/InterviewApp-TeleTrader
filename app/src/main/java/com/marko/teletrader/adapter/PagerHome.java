package com.marko.teletrader.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.marko.teletrader.ui.symbol.SymbolFragment;
import com.marko.teletrader.ui.news.NewsFragment;

public class PagerHome extends FragmentStateAdapter{

    private final static int NUM_OF_PAGES = 2;

    public PagerHome(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch(position){
            case 0:
                return SymbolFragment.newInstance();
            case 1:
            default:
                return NewsFragment.newInstance();
        }
    }

    @Override
    public int getItemCount(){
        return NUM_OF_PAGES;
    }



}
