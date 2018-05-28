package com.example.gifo.testapplication.utils.web.api.openweather;

import com.example.gifo.testapplication.utils.web.url.URLInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gifo on 25.05.2018.
 */

// Создаётся Retrofit-клиент для использования OpenWeatherMap API
public class OpenWeatherClient {

    private static Retrofit client = null;
    private static final String API_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "9d08d40a1aa1b222aa00a61bc055315e";
    private static final String API_KEY_NAME = "APPID";

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
