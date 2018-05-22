package com.example.gifo.testapplication.utils.web.api.geocoding;

import com.example.gifo.testapplication.utils.web.api.geocoding.models.Geolocation;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by gifo on 22.05.2018.
 */

public class GeocodingService {

    private static GeocodingInterface api = null;
    private static GeocodingInterface getApi() {
        if (api == null) api  = GeocodingClient.getClient().create(GeocodingInterface.class);
        return api;
    }

    public Geolocation getLocation(String locationName) {
        Call<Geolocation> messages = getApi().getAddress(locationName);
        try {
            Response<Geolocation> response = messages.execute();
            return response.body();
        } catch (IOException e) {
            return null;
        }
    }
}
