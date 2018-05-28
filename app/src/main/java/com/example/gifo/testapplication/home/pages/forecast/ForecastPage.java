package com.example.gifo.testapplication.home.pages.forecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gifo.testapplication.R;
import com.example.gifo.testapplication.home.pages.PageView;
import com.example.gifo.testapplication.service.WeatherService;
import com.example.gifo.testapplication.service.models.ForecastWeather;
import com.example.gifo.testapplication.utils.preferences.StringArray;

import java.util.ArrayList;

/**
 * Created by gifo on 21.05.2018.
 */

public class ForecastPage extends PageView {

    // listener для общения с MainActivity через интерфейс ForecastPageRecyclerInterface
    ForecastPageRecyclerInterface someEventListener;

    // Интерфейс для связи с MainActivity
    public interface ForecastPageRecyclerInterface {

        // Метод передаёт MainActivity инициализированные RecyclerView и RecyclerViewAdapter из фрагмента страницы ViewPager
        public void onInitializeForecastPageRecycler(Spinner forecastSpinner, RecyclerView view, ForecastRecyclerAdapter adapter);
    }

    public ForecastPage(View view, Context ctx) {
        super(view, ctx);
    }

    @Override
    protected void onAttach(Context parent) {
        try { someEventListener = (ForecastPageRecyclerInterface) parent;
        } catch (ClassCastException e) {
            throw new ClassCastException(parent.toString() + " must implement onSomeEventListener");
        }
    }

    @Override
    protected void onCreate(View view, Context parent) {

        // Отображаем в шапку Страницы (спиннер выбора города) последний в списке избранных город
        SharedPreferences appSettings = parent.getSharedPreferences("main", Context.MODE_PRIVATE);
        String[] cityesField = StringArray.getArray(appSettings.getString("CitiesField", ""));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(parent, android.R.layout.simple_spinner_item, cityesField);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = view.findViewById(R.id.city_for_forecast);
        spinner.setAdapter(adapter);
        int select_pos = 0;
        String select_city = appSettings.getString("SelectCity", "");
        for (int i=0; i<cityesField.length; i++) if (select_city.equals(cityesField[i])) select_pos = i;
        spinner.setSelection(select_pos);
        if ((cityesField.length == 1) && (cityesField[0].length() == 0)) spinner.setPrompt(parent.getResources().getString(R.string.forecast_is_city));

        // Задаём RecyclerView, определяем его отображение, задаём ему адаптер
        RecyclerView forecast = view.findViewById(R.id.recycler_forecast);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        forecast.setLayoutManager(mLayoutManager);
        ForecastRecyclerAdapter forecastAdapter =
                new ForecastRecyclerAdapter(parent, new ArrayList<ForecastWeather.DayliWeather>());
        forecast.setAdapter(forecastAdapter);

        // Передаём инициализированные RecyclerView и его адаптер в MainActivity через someEventListener-listener
        someEventListener.onInitializeForecastPageRecycler(spinner, forecast, forecastAdapter);
    }
}
