package com.example.gifo.testapplication.service.models.typedata;

import android.arch.persistence.room.TypeConverter;

import com.example.gifo.testapplication.service.models.ForecastWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by gifo on 27.05.2018.
 */

// Конвертер типа лист-DayliWeather в JSON формат для хранения в БД
public class DayliWeatherList {

    @TypeConverter
    public static ArrayList<ForecastWeather.DayliWeather> fromString(String value) {
        if (value == null) return new ArrayList<>();
        Type listType = new TypeToken<ArrayList<ForecastWeather.DayliWeather>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(ArrayList<ForecastWeather.DayliWeather> list) {
        return new Gson().toJson(list);
    }
}
