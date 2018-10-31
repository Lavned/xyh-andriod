package com.zejor;

import android.app.Application;
import android.content.Intent;

import com.ishumei.smantifraud.SmAntiFraud;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.moxie.client.manager.MoxieSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zejor.component.ApplicationComponent;
import com.zejor.component.DaggerApplicationComponent;
import com.zejor.module.ApplicationModule;
import com.zejor.mvp.activities.HomeActivity;
import com.zejor.utils.SharedPerferenceUtil;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.tongdun.android.shell.FMAgent;
import cn.tongdun.android.shell.exception.FMException;
import okhttp3.OkHttpClient;



public class App extends Application {
    public static App myApp;
    private ApplicationComponent applicationComponent;
    private String orderChangeStatus = "0";
    private int isOldMan = 1;
    private boolean toHome = false;
    private volatile long lastJump = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        try {
            FMAgent.init(this, FMAgent.ENV_PRODUCTION);
        } catch (FMException e) {
            e.printStackTrace();
        }
        //数美的初始化
        SmAntiFraud.SmOption smOption = new SmAntiFraud.SmOption();
        smOption.setOrganization("ylkj_android");
        SmAntiFraud.create(this, smOption);
        Logger.addLogAdapter(new AndroidLogAdapter());
        //魔蝎的初始化
        MoxieSDK.init(this);
        OkGo.getInstance().init(this);
    }

//
//    //初始化网络请求
    private void initOkGo() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
        headers.put("commonHeaderKey2", "commonHeaderValue2");
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");
        //----------------------------------------------------------------------------------------//

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(1000, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数
    }



    public void loginAgain() {
        if (System.currentTimeMillis() - lastJump > 2000) {
            lastJump = System.currentTimeMillis();
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            SharedPerferenceUtil.clear(this);
            setToHome(true);
            startActivity(intent);
        }
    }


    public static App getInstance() {
        return myApp;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public String getOrderChangeStatus() {
        return orderChangeStatus;
    }

    public void setOrderChangeStatus(String orderChangeStatus) {
        this.orderChangeStatus = orderChangeStatus;
    }

    public int getIsOldMan() {
        return isOldMan;
    }

    public void setIsOldMan(int isOldMan) {
        this.isOldMan = isOldMan;
    }

    public boolean isToHome() {
        return toHome;
    }

    public void setToHome(boolean toHome) {
        this.toHome = toHome;
    }
}
