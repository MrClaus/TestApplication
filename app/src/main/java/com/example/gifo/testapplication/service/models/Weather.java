package com.example.gifo.testapplication.service.models;

/**
 * Created by gifo on 26.05.2018.
 */

// Модельный класс - Информация о погоде
public class Weather {

    private float temperature; // температура
    private float pressure; // атмосферное давление
    private int humidity; // влажность воздуха
    private float wind; // скорость ветра
    private int clouds; // облачность

    private Weather() {} // блокируем вызов конструктора по умолчанию, так как данные передаются через конструктор
    public Weather(float temperature, float pressure, float wind, int clouds, int humidity) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.wind = wind;
        this.clouds = clouds;
        this.humidity = humidity;
    }

    // Возвращает температуру в Кельвинах
    public float getTemperature () {
        return temperature;
    }

    // Возвращает теемпературу в Цельсиях
    public float getCelsiusTemp () {
        return (temperature - 273.15f);
    }

    // Возвращает температуру в Фаренгейтах
    public float getFahrenheitTemp () {
        return (1.8f * getCelsiusTemp() + 32f);
    }

    public float getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getWind() {
        return wind;
    }

    public int getClouds() {
        return clouds;
    }
}
