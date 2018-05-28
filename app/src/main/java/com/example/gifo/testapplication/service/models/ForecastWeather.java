package com.example.gifo.testapplication.service.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.gifo.testapplication.service.models.typedata.DayliWeatherList;
import com.example.gifo.testapplication.service.models.typedata.HourlyWeatherList;
import com.example.gifo.testapplication.utils.web.api.openweather.models.ForecastDataset;

import java.util.ArrayList;

/**
 * Created by gifo on 26.05.2018.
 */

// Модельный класс прогноза погоды на несколько дней для сохранения в БД
@Entity
public class ForecastWeather {

    @PrimaryKey
    private int id; // ключ к объекту из БД

    // Список прогнозов на каждый день - DayliWeather (в свою очередь содержит список прогнозов на каждый час)
    @TypeConverters({DayliWeatherList.class})
    private ArrayList<DayliWeather> dayliList = new ArrayList<>();

    /* Альтернативные методы инициализации объекта
     * С приватными полями класса необходимо иметь публичные методы get/set
     * для данных полей класса, чтобы Room мог успешно работать с моделью - @Entity ForecastWeather
     * для добавления объекта в БД */

    // Задаёт id-объекта для хранения его в базе данных
    public void setId(int id) {
        this.id = id;
    }

    // Задаёт прогнозный лист на все дни
    public void setDayliList(ArrayList<DayliWeather> dayliList) {
        this.dayliList = dayliList;
    }

    // Возвращает id-объекта в базе данных
    public int getId() {
        return id;
    }

    // Возвращает прогнозный лист на все дни, для каждого из который имеется почасовой прогноз
    public ArrayList<DayliWeather> getDayliList() {
        return dayliList;
    }

    // Метод, который заполняет экземпляр текущего класса по передаваемому dataSet-у
    public void seData(ForecastDataset dataSet, int primaryKey) {
        this.id = primaryKey;
        int last_index = 0, post_index = 0;
        String days_item = new String();
        ArrayList<HourlyWeather> hourly_forecast = new ArrayList<>();
        for (int i = 0; i < dataSet.getSize(); i++) {
            String date_format = dataSet.getList().get(i).dt_txt;
            String days = date_format.substring(0, 10);
            String hours = date_format.substring(11, 13);
            if (i == 0) days_item = days;
            hourly_forecast.add(new HourlyWeather(
                    hours,
                    new Weather(
                            dataSet.getList().get(i).main.temp,
                            dataSet.getList().get(i).main.pressure,
                            dataSet.getList().get(i).wind.speed,
                            dataSet.getList().get(i).clouds.all,
                            dataSet.getList().get(i).main.humidity
                    )));
            if ((!days_item.equals(days)) || (i == (dataSet.getSize() - 1))) {
                ArrayList<HourlyWeather> copyed = new ArrayList<>();
                post_index = (i != (dataSet.getSize() - 1)) ? (i - 1) : i;
                for (int j = last_index; j <= post_index; j++) copyed.add(hourly_forecast.get(j));
                dayliList.add(new DayliWeather(days_item, copyed));
                days_item = days;
                last_index = i;
            }
        }
        hourly_forecast.clear();
    }

    // Вывод информации о текущем объекте в консоль (ДЛЯ ОТЛАДКИ)
    public void print() {
        System.out.println();
        System.out.println("/FORECAST WEATHER/");
        for (DayliWeather dayli : getDayliList()) {
            System.out.println("DATE: " + dayli.getDayDate());
            System.out.println("HOURS LIST:");
            for (HourlyWeather hourly : dayli.getHourlyList()) {
                System.out.println("-------- HOUR: " + hourly.getHour());
                System.out.println("-------- WEATHER:");
                System.out.println("---------------- temperature: " + hourly.getWeather().getTemperature());
                System.out.println("---------------- pressure: " + hourly.getWeather().getPressure());
                System.out.println("---------------- humidity: " + hourly.getWeather().getHumidity());
                System.out.println("---------------- speed_wind: " + hourly.getWeather().getWind());
                System.out.println("---------------- cloud_cover: " + hourly.getWeather().getClouds());
            }
            System.out.println();
        }
    }

    // Описывает модель погоды на 1 день
    public class DayliWeather {
        private String dayDate; // текущий день

        @TypeConverters({HourlyWeatherList.class})
        private ArrayList<HourlyWeather> hourlyList; // погодный список на каждый час в текущем дне

        private DayliWeather() {} // конструктор принимается только с заполнением данных
        public DayliWeather(String day_date, ArrayList<HourlyWeather> hourly_forecast) {
            this.dayDate = day_date;
            this.hourlyList = hourly_forecast;
        }

        public String getDayDate() {
            return dayDate;
        }

        public ArrayList<HourlyWeather> getHourlyList() {
            return hourlyList;
        }
    }

    // Описывает модель погоды на 1 час
    public class HourlyWeather {
        private String hour; // текущий час

        @Embedded
        private Weather weather; // погода на текущий час

        private HourlyWeather() {} // конструктор принимается только с заполнением данных
        public HourlyWeather(String hour, Weather weather) {
            this.hour = hour;
            this.weather = weather;
        }

        public String getHour() {
            return hour;
        }

        public Weather getWeather() {
            return weather;
        }
    }
}