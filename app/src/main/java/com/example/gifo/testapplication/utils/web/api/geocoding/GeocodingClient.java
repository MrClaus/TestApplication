package com.example.gifo.testapplication.utils.web.api.geocoding;

import com.example.gifo.testapplication.utils.web.api.ApikeyInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gifo on 22.05.2018.
 */

public class GeocodingClient {

    private static Retrofit client = null;
    private static final String API_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";
    private static final String API_KEY = "AIzaSyAgrFm2SMRZWJZcnUybjTdMCjZCvM33K1E";

    public static Retrofit getClient() {

        if (client == null) {

            //ApikeyInterceptor interceptorToken = new ApikeyInterceptor("","");
            HttpLoggingInterceptor interceptorLog = new HttpLoggingInterceptor();
            interceptorLog.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()


                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request request = original.newBuilder()
                                    .header("key", API_KEY)
                                    //.method(original.method(), original.body())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
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
