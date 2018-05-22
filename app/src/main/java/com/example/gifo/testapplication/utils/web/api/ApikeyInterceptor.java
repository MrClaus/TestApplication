package com.example.gifo.testapplication.utils.web.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gifo on 22.05.2018.
 */

public class ApikeyInterceptor implements Interceptor {

    private String apikey;
    private String parametrName;

    public ApikeyInterceptor(String parametrName, String apikey) {
        super();
        //this.apikey = apikey;
        //this.parametrName = parametrName;
    }

    /*@Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request original = chain.request();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder().addHeader(parametrName, apikey);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }*/

    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("key", "AIzaSyAgrFm2SMRZWJZcnUybjTdMCjZCvM33K1E")
                .method(original.method(), original.body())
                .build();
        Response response = chain.proceed(request);
        return response;
    }
}
