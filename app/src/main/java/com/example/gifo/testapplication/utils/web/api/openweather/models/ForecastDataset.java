package com.example.gifo.testapplication.utils.web.api.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gifo on 23.05.2018.
 */

// Класс-модель для загрузки представления данных из JSON (Описывает почасовой прогноз погоды для дальнейшего парсинга данных)
public class ForecastDataset {

    /* Парсинг JSON - заполнение объекта ForecastDataset соответствующими данными из JSON */

    @SerializedName("cod")
    private int cod; // код ответа от сервера на запрос

    @SerializedName("message")
    private float message;

    @SerializedName("cnt")
    private int count; // размер возвращаемого списка list (JSON)

    @SerializedName("list")
    private ArrayList<HoursForecast> weather_list; // почасовой список прогнозов

    @SerializedName("city")
    private City city; // информация о городе

    // JSON объект - 'list[ {...} ]'
    public class HoursForecast {

        @SerializedName("dt")
        public long dt; // Время прогнозирования данных, unix, UTC

        @SerializedName("main")
        public Main main; // обобщенные краткие данные о погоде

        @SerializedName("weather")
        public ArrayList<Weather> weather; // данные видуального отображения погоды

        @SerializedName("clouds")
        public Clouds clouds; // данные о облачности

        @SerializedName("wind")
        public Wind wind; // данные о ветре

        @SerializedName("rain")
        public Rain rain; // объект содержит данные об относительном количестве дождевых осадков за последние 3 часа

        @SerializedName("snow")
        public Snow snow; // объект содержит данные об относительном количестве снежных осадков за последние 3 часа

        @SerializedName("sys")
        public Sys sys;

        @SerializedName("dt_txt")
        public String dt_txt; // Время прогнозирования данных (Формат даты)

        // JSON объект - 'list[ {main} ]'
        public class Main {

            @SerializedName("temp")
            public float temp; // температура (Кельвины)

            @SerializedName("temp_min")
            public float temp_min; // минимальная температура

            @SerializedName("temp_max")
            public float temp_max; // максимальная температура

            @SerializedName("pressure")
            public float pressure;// атмосферное давление (если нет данных по sea_level, grnd_level)

            @SerializedName("sea_level")
            public float sea_level; // атмосферное давление на уровне моря

            @SerializedName("grnd_level")
            public float grnd_level; // атмосферное давление на уровне земли

            @SerializedName("humidity")
            public int humidity; // влажность, %

            @SerializedName("temp_kf")
            public float temp_kf;
        }

        // JSON объект - 'list[ {weather} ]'
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

        // JSON объект - 'list[ {clouds} ]'
        public class Clouds {
            @SerializedName("all")
            public int all; // облачность (%)
        }

        // JSON объект - 'list[ {wind} ]'
        public class Wind {

            @SerializedName("speed")
            public float speed; // скорость ветра (метр в секунду)

            @SerializedName("deg")
            public float deg; // направление ветра (градусы)
        }

        // JSON объект - 'list[ {rain} ]'
        public class Rain {
            @SerializedName("3h")
            public float _3h; // относительное количество дождевых осадков за последние 3 часа
        }

        // JSON объект - 'list[ {snow} ]'
        public class Snow {
            @SerializedName("3h")
            public float _3h; // относительное количество снежных осадков за последние 3 часа
        }

        // JSON объект - 'list[ {sys} ]'
        public class Sys {
            @SerializedName("pod")
            public String pod;
        }
    }

    // JSON объект - 'city'
    public class City {

        @SerializedName("id")
        public int id; // индекс города

        @SerializedName("name")
        public String name; // название города

        @SerializedName("coord")
        public Coordinates coord; // географические координаты местности

        @SerializedName("country")
        public String country; // код страны по типу ccTLD

        // JSON объект - 'city/coord'
        public class Coordinates {

            @SerializedName("lat")
            public double lat; // географическое значение широты

            @SerializedName("lon")
            public double lon; // географическое значение долготы
        }
    }

    /* Методы получения данных заполненного объекта ForecastDataset по образцу JSON */

    public boolean isResults() {
        return (cod == 200) ? true : false;
    }

    public int getResponseCode() {
        return cod;
    }

    public float getMessage() {
        return (isResults()) ? message : 0;
    }

    public int getSize() {
        return (isResults()) ? count : -1;
    }

    public ArrayList<HoursForecast> getList() {
        return (isResults()) ? weather_list : null;
    }

    public City getCityObject() {
        return (isResults()) ? city : null;
    }
}
