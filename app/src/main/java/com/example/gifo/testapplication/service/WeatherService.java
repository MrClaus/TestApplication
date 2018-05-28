package com.example.gifo.testapplication.service;

import com.example.gifo.testapplication.app.AppContext;
import com.example.gifo.testapplication.app.AppDatabase;
import com.example.gifo.testapplication.service.dao.CurrentWeatherDAO;
import com.example.gifo.testapplication.service.dao.ForecastWeatherDAO;
import com.example.gifo.testapplication.service.models.CurrentWeather;
import com.example.gifo.testapplication.service.models.ForecastWeather;
import com.example.gifo.testapplication.utils.web.api.geocoding.GeocodingService;
import com.example.gifo.testapplication.utils.web.api.geocoding.models.Geolocation;
import com.example.gifo.testapplication.utils.web.api.openweather.OpenWeatherService;
import com.example.gifo.testapplication.utils.web.api.openweather.models.ForecastDataset;
import com.example.gifo.testapplication.utils.web.api.openweather.models.WeatherDataset;

/**
 * Created by gifo on 26.05.2018.
 */

// Сервис по получению информации о погоде и обновлению данных в базе данных приложения
public class WeatherService {

    private String log;
    private String city = new String();
    private String last_city = new String();
    private Double city_coord_lat, city_coord_lon;

    private static GeocodingService geoService;
    private static OpenWeatherService weatherService;
    private static WeatherService myService = null;

    // Возвращает объект - погодный сервис для удобного получения информации о погоде из сети
    public static WeatherService getService() {
        if (myService == null) {
            myService = new WeatherService();
            geoService = new GeocodingService();
            weatherService = new OpenWeatherService();
        }
        return myService;
    }

    // Задаётся город для получение прогноза погоды
    public void setCity(String name) {
        city = name;
    }

    // Возвращает текущий город, на который запрашивается прогноз
    public String getCity() {
        return city;
    }

    // Запускает обновление данных по сети - прогноз погоды на заданный город
    public void refreshData() {
        if ((city != null) && (city.length() > 0)) {
            if (!last_city.equals(city)) {
                Geolocation geoObject = geoService.getLocation(city);
                city_coord_lat = geoObject.getLocationLat();
                city_coord_lon = geoObject.getLocationLong();
                log = geoObject.getResponseStatus();
            }
            if ((city_coord_lat != null) && (city_coord_lon != null)) {
                WeatherDataset weatherData = weatherService.getCurrentWeather(city_coord_lat, city_coord_lon);
                ForecastDataset forecastData = weatherService.getForecastWeather(city_coord_lat, city_coord_lon);
                if (weatherData.isResults() && forecastData.isResults()) {
                    AppDatabase db = AppContext.getContext().getDatabase();

                    CurrentWeather currentWeather = new CurrentWeather();
                    currentWeather.setData(weatherData, 1);
                    CurrentWeatherDAO currentWeatherDAO = db.currentWeatherDAO();
                    currentWeatherDAO.insert(currentWeather);

                    ForecastWeather forecastWeather = new ForecastWeather();
                    forecastWeather.seData(forecastData, 1);
                    ForecastWeatherDAO forecastWeatherDAO = db.forecastWeatherDAO();
                    forecastWeatherDAO.insert(forecastWeather);

                    log = "UPDATE_SUCCESSFUL";
                }
            } else if (log.equals("ZERO_RESULTS")) {} // вызвать callback метод через интерфейс - уведомление о неправильном городе
            last_city = city;
        }
    }
}
