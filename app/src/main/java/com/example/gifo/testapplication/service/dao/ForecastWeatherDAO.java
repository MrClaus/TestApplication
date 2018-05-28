package com.example.gifo.testapplication.service.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.gifo.testapplication.service.models.ForecastWeather;

/**
 * Created by gifo on 27.05.2018.
 */

// Интерфейс DAO для работы с сущностью ForecastWeather в БД через Room
@Dao
public interface ForecastWeatherDAO {

    // Поиск объекта по id в БД
    @Query("SELECT * FROM ForecastWeather WHERE id = :id")
    LiveData<ForecastWeather> getById(int id);

    // Добавление или замена уже существующего объекта в БД
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ForecastWeather model);
}
