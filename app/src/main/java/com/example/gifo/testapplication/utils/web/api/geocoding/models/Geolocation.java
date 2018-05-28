package com.example.gifo.testapplication.utils.web.api.geocoding.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gifo on 22.05.2018.
 */

// Класс-модель для загрузки представления данных из JSON (Описывает геолокацию)
public class Geolocation {

    /* Парсинг JSON - заполнение объекта Geolocation соответствующими данными из JSON */

    @SerializedName("status")
    private String status; // Статус ответа на запрос - ОК - если успешно

    @SerializedName("results")
    private ArrayList<Results> results;

    // JSON объект - 'results'
    private class Results {
        @SerializedName("address_components")
        ArrayList<AddressComponents> list_address;

        @SerializedName("geometry")
        Geometry geometry;

        @SerializedName("place_id")
        String place_id;
    }

    // JSON объект - 'results/address_components'
    private class AddressComponents {
        @SerializedName("long_name")
        String long_name;

        @SerializedName("short_name")
        String short_name;
    }

    // JSON объект - 'results/geometry'
    private class Geometry {
        @SerializedName("location")
        Location location;
    }

    // JSON объект - 'results/geometry/location'
    private class Location {
        @SerializedName("lat")
        double lat;

        @SerializedName("lng")
        double lng;
    }

    /* Методы получения данных заполненного объекта Geolocation по образцу JSON */

    // Возвращает - true - если JSON загрузил модель и объект Кesults - не пустой
    public boolean isResults() {
        return (status.equals("OK") && (results != null) && (!results.isEmpty())) ? true : false;
    }

    // Возвращает ответ от сервера о статусе запроса
    public String getResponseStatus() {
        return status;
    }

    // Возвращает название города по ответу из JSON
    public String getCity() {
        if (isResults()) {
            ArrayList<AddressComponents> list_address = results.get(0).list_address;
            return ((list_address != null) && (!list_address.isEmpty())) ? list_address.get(0).short_name : null;
        } else return null;
    }

    // Возвращает название страны по ответу из JSON
    public String getCountry() {
        if (isResults()) {
            ArrayList<AddressComponents> list_address = results.get(0).list_address;
            return ((list_address != null) && (!list_address.isEmpty())) ? list_address.get(list_address.size() - 1).long_name : null;
        } else return null;
    }

    // Возвращает код страны в формате - ccTLD по ответу из JSON
    public String getCountryTLD() {
        if (isResults()) {
            ArrayList<AddressComponents> list_address = results.get(0).list_address;
            return ((list_address != null) && (!list_address.isEmpty())) ? list_address.get(list_address.size() - 1).short_name : null;
        } else return null;
    }

    // Возвращает ID местности на мировой карте по ответу из JSON
    public String getPlaceID() {
        return (isResults()) ? results.get(0).place_id : null;
    }

    // Возвращает значение широты на мировой карте по ответу из JSON
    public Double getLocationLat() {
        if (isResults()) {
            Location loc = results.get(0).geometry.location;
            return (loc != null) ? loc.lat : null;
        } else return null;
    }

    // Возвращает значение долготы на мировой карте по ответу из JSON
    public Double getLocationLong() {
        if (isResults()) {
            Location loc = results.get(0).geometry.location;
            return (loc != null) ? loc.lng : null;
        } else return null;
    }
}
