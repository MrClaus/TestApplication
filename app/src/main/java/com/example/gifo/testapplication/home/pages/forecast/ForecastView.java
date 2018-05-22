package com.example.gifo.testapplication.home.pages.forecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.home.pages.PageView;
import com.example.gifo.testapplication.utils.preferences.StringArray;

import java.util.ArrayList;

/**
 * Created by gifo on 21.05.2018.
 */

public class ForecastView extends PageView {

    public ForecastView(View view) {
        super(view);
    }

    @Override
    protected void onCreate(View view) {
        TextView cityName = view.findViewById(R.id.city_for_forecast);

        // Загружаем список избранных городов из настроек - SharedPreferences
        SharedPreferences appSettings = view.getContext().getSharedPreferences("main", Context.MODE_PRIVATE);
        String citiesFieldPref = appSettings.getString("CitiesField", "");
        ArrayList<String> citiesField = StringArray.getList(citiesFieldPref);

        if (!citiesField.isEmpty()) cityName.setText(citiesField.get(citiesField.size() - 1));
    }
}
