package com.example.gifo.testapplication.utils.web.api.geocoding;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.gifo.testapplication.utils.web.api.geocoding.models.Geolocation;

/**
 * Created by gifo on 22.05.2018.
 */

// Интерфейс поддерживающих методов сервиса - GeocodingService
public interface GeocodingInterface {

    /* Возвращает json-данные геолокации (которую мы ищем по названию 'address')
       и парсит в модель объекта Geolocation */

    @GET("json")
    Call<Geolocation> getAddress(@Query("address") String cityName);
}
