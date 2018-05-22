package com.example.gifo.testapplication.settings.favcity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.home.pages.forecast.ForecastRecyclerAdapter;
import com.example.gifo.testapplication.utils.preferences.StringArray;

import java.util.ArrayList;

/**
 * Created by gifo on 18.05.2018.
 */

public class FavoritesCityRecyclerView extends Fragment {

    onSomeEventListener someEventListener; // слушатель событий onSomeEventListener

    // Интерфейс для связки событий между текущим фракментом и родительским активити
    public interface onSomeEventListener {
        public void recyclerCityAdapter(FavoritesCityRecyclerAdapter adapter);
    }

    // Связываем слушатель событий someEventListener с родительским активити
    @Override
    public void onAttach(Context ctx) {
        super.onAttach(ctx);
        if (ctx instanceof Activity) {
            try { someEventListener = (onSomeEventListener) ctx;
            } catch (ClassCastException e) {
                throw new ClassCastException(ctx.toString() + " must implement onSomeEventListener");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_vertical, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView mRecyclerView = view.findViewById(R.id.recycler_vertical);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Загружаем список избранных городов из настроек - SharedPreferences
        SharedPreferences appSettings = getContext().getSharedPreferences("main", Context.MODE_PRIVATE);
        String citiesFieldPref = appSettings.getString("CitiesField", "");
        ArrayList<String> citiesField = StringArray.getList(citiesFieldPref);

        FavoritesCityRecyclerAdapter mAdapter = new FavoritesCityRecyclerAdapter(citiesField, getContext());
        mRecyclerView.setAdapter(mAdapter);

        // Передаём кастомный адаптер родительскому активити для дальнейших операций со списком
        someEventListener.recyclerCityAdapter(mAdapter);
    }
}
