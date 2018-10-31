package com.zejor.retrofit;


import com.zejor.contants.CommonValueUtil;
import com.zejor.retrofit.retrofit_request_response.JsonConverterFactory;

import retrofit2.Retrofit;


public class RetrofitFactory {

    private static RetrofitFactory retrofitFactory = null;
    private Retrofit retrofit;

    private RetrofitFactory(){
        retrofit =new Retrofit.Builder()
                .client(OkHttpClientFactory.getInstance().getOkHttpClient())
                .addConverterFactory(JsonConverterFactory.create())
                .baseUrl(CommonValueUtil.URL)
                .build();
    }

    public static RetrofitFactory getInstance(){
        if(retrofitFactory == null){
            retrofitFactory = new RetrofitFactory();
        }
        return retrofitFactory;
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
