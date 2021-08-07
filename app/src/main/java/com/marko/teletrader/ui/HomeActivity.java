package com.marko.teletrader.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.marko.teletrader.adapter.PagerHome;
import com.marko.teletrader.databinding.HomeActivityBinding;

public class HomeActivity extends AppCompatActivity{

    HomeActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = HomeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        confPager();
    }

    private void confPager(){
        PagerHome adapter = new PagerHome(this);
        binding.pagerView.setAdapter(adapter);

        new TabLayoutMediator(binding.tabView, binding.pagerView, this::confTabText).attach();
    }

    private void confTabText(TabLayout.Tab tab, int position){
        switch(position){
            case 0:
                tab.setText("Symbol");
                break;
            case 1:
            default:
                tab.setText("News");
                break;
        }
    }

}