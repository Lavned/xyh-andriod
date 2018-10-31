package com.zejor.module;

import com.zejor.App;
import com.zejor.contants.Api;
import com.zejor.contants.ApiService;
import com.zejor.contants.CommonValueUtil;
import com.zejor.net.RetrofitConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    @Provides
    OkHttpClient.Builder provideOkHttpClient() {
        Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        return new OkHttpClient().newBuilder()
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(RetrofitConfig.sLoggingInterceptor)
                .addInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .addNetworkInterceptor(RetrofitConfig.sRewriteCacheControlInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS);
    }


    @Provides
    Api provideApis(OkHttpClient.Builder builder) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build());

        return Api.getInstance(retrofitBuilder
                .baseUrl(CommonValueUtil.URL)
                .build().create(ApiService.class));
    }
}
