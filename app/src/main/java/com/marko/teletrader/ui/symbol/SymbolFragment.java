package com.marko.teletrader.ui.symbol;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.marko.teletrader.AppUtil;
import com.marko.teletrader.R;
import com.marko.teletrader.adapter.SymbolAdapter;
import com.marko.teletrader.databinding.SymbolFragmentBinding;
import com.marko.teletrader.model.symbol.Symbol;

import java.util.List;

public class SymbolFragment extends Fragment{

    private SymbolAdapter symbolAdapter;
    private SymbolFragmentBinding binding;
    private SymbolViewModel newsViewModel;

    public static SymbolFragment newInstance(){
        return new SymbolFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = SymbolFragmentBinding.inflate(getLayoutInflater(), container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        defaultUI();
        confListAndAdapter();
        confViewModel();

        binding.listContainer.setOnRefreshListener(newsViewModel::loadSymbol);
        symbolAdapter.setOnRemoveItemListener(newsViewModel::removeWithId);
    }

    private void confViewModel(){
        newsViewModel = new ViewModelProvider(this).get(SymbolViewModel.class);
        newsViewModel.getSymbolList().observe(getViewLifecycleOwner(), this::updateUI);
        newsViewModel.getMsg().observe(getViewLifecycleOwner(), msg ->
                AppUtil.showToast(getActivity(), msg)
        );
    }

    private void confListAndAdapter(){
        if(getActivity()==null)
            return;

        symbolAdapter = new SymbolAdapter(getActivity());
        binding.listView.setAdapter(symbolAdapter);

        if(binding.listView.getItemAnimator() !=null)
            ((SimpleItemAnimator) binding.listView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void defaultUI(){
        binding.msgTxt.setText(R.string.loadingTxt);
        toggleVisibleViews(false);
    }

    private void updateUI(List<Symbol> symbolList){
        symbolAdapter.setSymbolList(symbolList);
        binding.listContainer.setRefreshing(false);

        if(symbolList==null || symbolList.isEmpty()){
            toggleVisibleViews(false);
            binding.msgTxt.setText(R.string.noItemsTxt);
        }else{
            toggleVisibleViews(true);
            binding.msgTxt.setText("");
        }
    }

    private void toggleVisibleViews(boolean isListVisible){
        binding.listContainer.setVisibility(isListVisible ? View.VISIBLE : View.GONE);
        binding.msgTxt.setVisibility(isListVisible ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater){
        inflater.inflate(R.menu.symbol_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        switch (item.getItemId()) {

            case R.id.sortDefault:
                newsViewModel.sortDefault();
                break;
            case R.id.ascending:
                newsViewModel.sortAscending();
                break;
            case R.id.descending:
                newsViewModel.sortDescending();
                break;

            case R.id.changeLast:
                symbolAdapter.setFormatMode(SymbolAdapter.FORMAT_CHANGE_LAST);
                break;
            case R.id.bidAsk:
                symbolAdapter.setFormatMode(SymbolAdapter.FORMAT_BID_ASK);
                break;

        }

        return super.onOptionsItemSelected(item);
    }


}