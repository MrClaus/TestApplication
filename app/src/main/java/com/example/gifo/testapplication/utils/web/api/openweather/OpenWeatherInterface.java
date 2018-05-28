package com.example.gifo.testapplication.utils.web.api.openweather;

import com.example.gifo.testapplication.utils.web.api.openweather.models.ForecastDataset;
import com.example.gifo.testapplication.utils.web.api.openweather.models.WeatherDataset;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gifo on 25.05.2018.
 */

// Интерфейс поддерживающих методов сервиса - OpenWeatherService
public interface OpenWeatherInterface {

    /* Возвращает json-данные почасового прогноза погоды (который мы получаем по гео-данным 'lat' и 'lon')
       и парсит в модель объекта ForecastDataset */

    @GET("forecast")
    Call<ForecastDataset> getForecastWeather(@Query("lat") double geo_lat, @Query("lon") double geo_lon);


    /* Возвращает json-данные текущего прогноза погоды (который мы получаем по гео-данным 'lat' и 'lon')
       и парсит в модель объекта WeatherDataset */

    @GET("weather")
    Call<WeatherDataset> getCurrentWeather(@Query("lat") double geo_lat, @Query("lon") double geo_lon);
}
