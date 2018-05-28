package com.example.gifo.testapplication.utils.web.api.openweather;

import com.example.gifo.testapplication.utils.web.api.openweather.models.ForecastDataset;
import com.example.gifo.testapplication.utils.web.api.openweather.models.WeatherDataset;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by gifo on 25.05.2018.
 */

public class OpenWeatherService {

    private static OpenWeatherInterface api = null;
    private static OpenWeatherInterface getApi() {
        if (api == null) api  = OpenWeatherClient.getClient().create(OpenWeatherInterface.class);
        return api;
    }

    // Возвращает объект WeatherDataset (текущий прогноз погоды) по географическим координатам широты и долготы
    public WeatherDataset getCurrentWeather(double geo_lat, double geo_lon) {
        Call<WeatherDataset> messages = getApi().getCurrentWeather(geo_lat, geo_lon);
        try {
            Response<WeatherDataset> response = messages.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }

    // Возвращает объект ForecastDataset (почасовой прогноз погоды на 5 дней)
    // по географическим координатам широты и долготы
    public ForecastDataset getForecastWeather(double geo_lat, double geo_lon) {
        Call<ForecastDataset> messages = getApi().getForecastWeather(geo_lat, geo_lon);
        try {
            Response<ForecastDataset> response = messages.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }
}
