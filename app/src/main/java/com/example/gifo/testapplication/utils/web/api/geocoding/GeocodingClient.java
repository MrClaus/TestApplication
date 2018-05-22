package com.example.gifo.testapplication.utils.web.api.geocoding;

import com.example.gifo.testapplication.utils.web.url.URLInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gifo on 22.05.2018.
 */

// Создаётся Retrofit-клиент для использования Geocoding API (Google Maps APIs)
public class GeocodingClient {

    private static Retrofit client = null;
    private static final String API_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";
    private static final String API_KEY = "AIzaSyAgrFm2SMRZWJZcnUybjTdMCjZCvM33K1E";
    private static final String API_KEY_NAME = "key";

    public static Retrofit getClient() {

        if (client == null) {

            URLInterceptor interceptorToken = new URLInterceptor(API_KEY_NAME, API_KEY);
            HttpLoggingInterceptor interceptorLog = new HttpLoggingInterceptor();
            interceptorLog.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptorToken)
                    .addInterceptor(interceptorLog)
                    .build();

            client = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return client;
    }
}
