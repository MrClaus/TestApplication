package com.example.gifo.testapplication.utils.web.api.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gifo on 23.05.2018.
 */

// Класс-модель для загрузки представления данных из JSON (Описывает текущую погоду для дальнейшего парсинга данных)
public class WeatherDataset {

    /* Парсинг JSON - заполнение объекта WeatherDataset соответствующими данными из JSON */

    @SerializedName("coord")
    private Coordinates coord; // географические координаты местности

    @SerializedName("weather")
    private ArrayList<Weather> weather; // данные видуального отображения погоды

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private Main main; // обобщенные краткие данные о погоде

    @SerializedName("wind")
    private Wind wind; // данные о ветре

    @SerializedName("clouds")
    private Clouds clouds; // данные о облачности

    @SerializedName("rain")
    private Rain rain; // объект содержит данные об относительном количестве дождевых осадков за последние 3 часа

    @SerializedName("snow")
    private Snow snow; // объект содержит данные об относительном количестве снежных осадков за последние 3 часа

    @SerializedName("dt")
    private long dt; // время прогнозирования данных, unix, UTC

    @SerializedName("sys")
    private Sys sys; // прочие данные о географическом расположении и погоде

    @SerializedName("id")
    private int id; // индекс города

    @SerializedName("name")
    private String name; // название города

    @SerializedName("cod")
    private int cod; // код ответа от сервера на запрос

    // JSON объект - 'coord'
    public class Coordinates {
        @SerializedName("lon")
        public float lon; // географическое значение долготы

        @SerializedName("lat")
        public float lat; // географическое значение широты
    }

    // JSON объект - 'weather[ {Weather} ]'
    public class Weather {
        @SerializedName("id")
        public int id; // индекс условия погоды

        @SerializedName("main")
        public String main; // группа параметров погоды (дождь, снег, экстрим и ...)

        @SerializedName("description")
        public String description; // погодные условия в возвращаемой группе main

        @SerializedName("icon")
        public String icon; // индекс графического значка погоды
    }

    // JSON объект - 'main'
    public class Main {
        @SerializedName("temp")
        public float temp; // температура (Кельвины)

        @SerializedName("pressure")
        public float pressure; // атмосферное давление (если нет данных по sea_level, grnd_level)

        @SerializedName("humidity")
        public int humidity; // влажность, %

        @SerializedName("temp_min")
        public float temp_min; // минимальная температура

        @SerializedName("temp_max")
        public float temp_max; // максимальная температура

        @SerializedName("sea_level")
        public float sea_level; // атмосферное давление на уровне моря

        @SerializedName("grnd_level")
        public float grnd_level; // атмосферное давление на уровне земли
    }

    // JSON объект - 'wind'
    public class Wind {
        @SerializedName("speed")
        public float speed; // скорость ветра (метр в секунду)

        @SerializedName("deg")
        public float deg; // направление ветра (градусы)
    }

    // JSON объект - 'clouds'
    public class Clouds {
        @SerializedName("all")
        public int all; // облачность (%)
    }

    // JSON объект - 'rain'
    public class Rain {
        @SerializedName("3h")
        public float _3h; // относительное количество дождевых осадков за последние 3 часа
    }

    // JSON объект - 'snow'
    public class Snow {
        @SerializedName("3h")
        public float _3h; // относительное количество снежных осадков за последние 3 часа
    }

    // JSON объект - 'sys'
    public class Sys {
        @SerializedName("message")
        public float message;

        @SerializedName("country")
        public String country; // код страны по типу ccTLD

        @SerializedName("sunrise")
        public long sunrise; // Время восхода, unix, UTC

        @SerializedName("sunset")
        public long sunset; // Время заката, unix, UTC
    }

    /* Методы получения данных заполненного объекта WeatherDataset по образцу JSON */

    public boolean isResults() {
        return (cod == 200) ? true : false;
    }

    public boolean isRaining() {
        return (rain != null) ? true : false;
    }

    public boolean isSnowing() {
        return (snow != null) ? true : false;
    }

    public Coordinates getCoordinates() {
        return (isResults()) ? coord : null;
    }

    public Weather getWeather() {
        return (isResults()) ? weather.get(0) : null;
    }

    public String getBase() {
        return (isResults()) ? base : null;
    }

    public Main getMain() {
        return (isResults()) ? main : null;
    }

    public Wind getWind() {
        return (isResults()) ? wind : null;
    }

    public Clouds getClouds() {
        return (isResults()) ? clouds : null;
    }

    public Rain getRaining() {
        return (isResults() && isRaining()) ? rain : null;
    }

    public Snow getSnowing() {
        return (isResults() && isSnowing()) ? snow : null;
    }

    public long getDt() {
        return (isResults()) ? dt : 0;
    }

    public Sys getSys() {
        return (isResults()) ? sys : null;
    }

    public String getCity() {
        return (isResults()) ? name : null;
    }

    public int getCityID() {
        return (isResults()) ? id : -1;
    }

    public int getResponseCode() {
        return cod;
    }
}
