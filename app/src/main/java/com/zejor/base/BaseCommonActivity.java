package com.zejor.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zejor.R;
import com.zejor.Utils;
import com.zejor.utils.PermissionUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


/**
 * 自定义公共类
 */
public abstract class BaseCommonActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private View mRootView;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        noTitle();
        titleColor();
        //权限申请
        if (Build.VERSION.SDK_INT >= 23) {
            if ((!PermissionUtils.hasPermissons(this, ACCESS_COARSE_LOCATION))
                    || (!PermissionUtils.hasPermissons(this, READ_PHONE_STATE))
                    || (!PermissionUtils.hasPermissons(this, ACCESS_FINE_LOCATION))
                    || (!PermissionUtils.hasPermissons(this, WRITE_EXTERNAL_STORAGE))
                    || (!PermissionUtils.hasPermissons(this, READ_EXTERNAL_STORAGE))) {
                requestPermissions(new String[]{
                        ACCESS_COARSE_LOCATION,  //必选
                        ACCESS_FINE_LOCATION,
                        READ_PHONE_STATE,  //必选
                        WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE
                }, 100);
            }
        }
    }

    public void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void noTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void titleColor() {
        Utils.setWindowStatusBarColor(this, R.color.green);
    }

}
