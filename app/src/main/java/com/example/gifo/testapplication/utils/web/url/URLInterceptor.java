package com.example.gifo.testapplication.utils.web.url;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gifo on 23.05.2018.
 */

// Дополняет URL-адрес передаваемыми аргументами параметра и его значения
public class URLInterceptor implements Interceptor {

    private String parameter, value;

    public URLInterceptor(String parameter, String value) {
        super();
        this.parameter = parameter;
        this.value = value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl fullPath = originalRequest.url().newBuilder()
                .addQueryParameter(parameter, value)
                .build();
        Request.Builder newRequestBuilder = originalRequest.newBuilder().url(fullPath);
        Request newRequest = newRequestBuilder.build();
        return chain.proceed(newRequest);
    }
}
