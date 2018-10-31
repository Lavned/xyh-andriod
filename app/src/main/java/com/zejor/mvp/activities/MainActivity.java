package com.zejor.mvp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.zejor.R;
import com.zejor.Utils;
import com.zejor.base.BaseActivity;
import com.zejor.component.ApplicationComponent;
import com.zejor.utils.SharedPerferenceUtil;

public class MainActivity extends BaseActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onRetry() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkConnected(MainActivity.this)){

            Toast.makeText(MainActivity.this,"当前网络无连接，请检查网络",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isNetworkConnected(Context context) {
         if (context != null) {
             ConnectivityManager mConnectivityManager = (ConnectivityManager) context
             .getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
             if (mNetworkInfo != null) {
                 return mNetworkInfo.isAvailable();
                 }
             }
         return false;
         }
    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }


    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        if ((boolean) SharedPerferenceUtil.getData(this, "first", true)) {
            intent = new Intent(MainActivity.this, ViewPagerActivity.class);
        } else {
            intent = new Intent(MainActivity.this, HomeActivity.class);

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

}
