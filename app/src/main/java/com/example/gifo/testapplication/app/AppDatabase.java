package com.example.gifo.testapplication.app;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.gifo.testapplication.service.dao.CurrentWeatherDAO;
import com.example.gifo.testapplication.service.dao.ForecastWeatherDAO;
import com.example.gifo.testapplication.service.models.CurrentWeather;
import com.example.gifo.testapplication.service.models.ForecastWeather;

/**
 * Created by gifo on 27.05.2018.
 */

// Создаётся База Данных приложения версии version = 1
// База данных содержит две таблица CurrentWeather и ForecastWeather

@Database(entities = {CurrentWeather.class, ForecastWeather.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Интерфейсы для работы с таблицами БД (CurrentWeather и ForecastWeather)
    public abstract CurrentWeatherDAO currentWeatherDAO();
    public abstract ForecastWeatherDAO forecastWeatherDAO();
}
