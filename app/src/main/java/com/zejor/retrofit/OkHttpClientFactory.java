package com.zejor.retrofit;

import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpClientFactory {

    private static OkHttpClientFactory okHttpClientFactory = null;
    private OkHttpClient okHttpClient;
    private static final long readTimeout = 15;
    private static final long connectTimeout = 15;

    private OkHttpClientFactory() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.e("request == " + message);

            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CustomerInterceptor())
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClientFactory getInstance() {
        if (okHttpClientFactory == null) {
            okHttpClientFactory = new OkHttpClientFactory();
        }
        return okHttpClientFactory;
    }


    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
