package com.example.gifo.testapplication.service.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.example.gifo.testapplication.utils.web.api.openweather.models.WeatherDataset;

/**
 * Created by gifo on 26.05.2018.
 */

// Модельный класс прогноза погоды на текущее время для сохранения в БД
@Entity
public class CurrentWeather {

    @PrimaryKey
    private int id; // ключ к объекту из БД

    @Embedded
    private Weather weather; // прогноз погоды

    /* Альтернативные методы инициализации объекта
     * С приватными полями класса необходимо иметь публичные методы get/set
     * для данных полей класса, чтобы Room мог успешно работать с моделью - @Entity CurrentWeather
     * для добавления объекта в БД */

    // Задаёт id-объекта для хранения его в базе данных
    public void setId(int id) {
        this.id=id;
    }

    // Заполняет объект Weather - прогноз погоды
    public void setWeather(Weather weather) {
        this.weather=weather;
    }

    // Возвращает id-объекта в базе данных
    public int getId() {
        return id;
    }

    // Возвращает текущий прогноз погоды
    public Weather getWeather() {
        return weather;
    }

    // Метод, который заполняет экземпляр текущего класса по передаваемому dataSet-у
    public void setData(WeatherDataset dataSet, int primaryKey) {
        this.id = primaryKey;
        weather = new Weather(
            dataSet.getMain().temp,
            dataSet.getMain().pressure,
            dataSet.getWind().speed,
            dataSet.getClouds().all,
            dataSet.getMain().humidity
        );
    }

    // Вывод информации о текущем объекте в консоль (ДЛЯ ОТЛАДКИ)
    public void print() {
        System.out.println();
        System.out.println("/CURRENT WEATHER/");
        System.out.println("WEATHER:");
        System.out.println("-------- temperature: " + getWeather().getTemperature());
        System.out.println("-------- pressure: " + getWeather().getPressure());
        System.out.println("-------- humidity: " + getWeather().getHumidity());
        System.out.println("-------- speed_wind: " + getWeather().getWind());
        System.out.println("-------- cloud_cover: " + getWeather().getClouds());
        System.out.println();
    }
}
