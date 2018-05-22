package com.example.gifo.testapplication.utils.web.api.geocoding;

import com.example.gifo.testapplication.utils.web.api.geocoding.models.Geolocation;

/**
 * Created by gifo on 22.05.2018.
 */

public class Test {
    public static void main(String[] args) {
        GeocodingService geoService = new GeocodingService();
        Geolocation loc = geoService.getLocation("Penza");
        System.out.println(loc.getCity());
        System.out.println(loc.getCountry());
        System.out.println(loc.getCountryTLD());
        System.out.println(loc.getLocationLat());
        System.out.println(loc.getLocationLong());
        System.out.println(loc.getPlaceID());
    }
}
