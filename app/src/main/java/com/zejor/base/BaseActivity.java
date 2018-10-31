package com.zejor.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.RxActivity;
import com.zejor.App;
import com.zejor.R;
import com.zejor.Utils;
import com.zejor.utils.LoadingDialogUtil;
import com.zejor.utils.PermissionUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public abstract class BaseActivity<T1 extends BaseContract.BasePresenter> extends RxActivity implements IBase, BaseContract.BaseView {





//    private RequestPermissionCallBack mRequestPermissionCallBack;
//    private final int mRequestCode = 1024;

    private Unbinder unbinder;
    private View mRootView;
    @Nullable
    @Inject
    protected T1 mPresenter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        noTitle();
        titleColor();
        mRootView = createView(null, null, savedInstanceState);
        setContentView(mRootView);
        bindView(mRootView, savedInstanceState);
        initInjector(App.getInstance().getApplicationComponent());
        attachView();
        initData();
//        if (Build.VERSION.SDK_INT >= 23) {
//            if ((!PermissionUtils.hasPermissons(this, ACCESS_COARSE_LOCATION))
//                    || (!PermissionUtils.hasPermissons(this, READ_PHONE_STATE))
//                    || (!PermissionUtils.hasPermissons(this, ACCESS_FINE_LOCATION))
//                    || (!PermissionUtils.hasPermissons(this, WRITE_EXTERNAL_STORAGE))
//                    || (!PermissionUtils.hasPermissons(this, READ_EXTERNAL_STORAGE))) {
//                requestPermissions(new String[]{
//                        ACCESS_COARSE_LOCATION,
//                        ACCESS_FINE_LOCATION,
//                        READ_PHONE_STATE,
//                        WRITE_EXTERNAL_STORAGE,
//                        READ_EXTERNAL_STORAGE
//                }, 100);
//            }
//        }
    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(getContentLayout(), container);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public View getView() {
        return mRootView;
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.<T>bindToLifecycle();
    }

    @Override
    public void showLoading() {
        LoadingDialogUtil.getInstance().showDialog(context);
    }
    @Override
    public void showSuccess(@Nullable String msg) {
        if (LoadingDialogUtil.getInstance().isShowing()) {
            LoadingDialogUtil.getInstance().stopDialog();
        }
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showFaild() {
        if (LoadingDialogUtil.getInstance().isShowing()) {
            LoadingDialogUtil.getInstance().stopDialog();
        }
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoNet() {
        if (LoadingDialogUtil.getInstance().isShowing()) {
            LoadingDialogUtil.getInstance().stopDialog();
        }
    }

    /**
     * 全屏
     */
    public void fullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 去掉标题栏
     */
    public void noTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     *
     */
    public void titleColor() {
        Utils.setWindowStatusBarColor(this, R.color.green);
    }



//    /**
//     * 权限请求结果回调
//     *
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        boolean hasAllGranted = true;
//        StringBuilder  permissionName = new StringBuilder();
//        for (String s: permissions) {
//            permissionName = permissionName.append(s + "\r\n");
//        }
//        switch (requestCode) {
//            case mRequestCode: {
//                for (int i = 0; i < grantResults.length; ++i) {
//                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
//                        hasAllGranted = false;
//                        //在用户已经拒绝授权的情况下，如果shouldShowRequestPermissionRationale返回false则
//                        // 可以推断出用户选择了“不在提示”选项，在这种情况下需要引导用户至设置页手动授权
//                        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
//                            new AlertDialog.Builder(this)
//                                    .setMessage("【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。" +
//                                            "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】\r\n" +
//                                            "获取相关权限失败:\r\n" +
//                                            permissionName +
//                                            "将导致部分功能无法正常使用，需要到设置页面手动授权")
//                                    .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            Uri uri = Uri.fromParts("package", getApplicationContext().getPackageName(), null);
//                                            intent.setData(uri);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            mRequestPermissionCallBack.denied();
//                                        }
//                                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
//                                    mRequestPermissionCallBack.denied();
//                                }
//                            }).show();
//
//                        } else {
//                            //用户拒绝权限请求，但未选中“不再提示”选项
//                            mRequestPermissionCallBack.denied();
//                        }
//                        break;
//                    }
//                }
//                if (hasAllGranted) {
//                    mRequestPermissionCallBack.granted();
//                }
//            }
//        }
//    }
//
//    /**
//     * 发起权限请求
//     *
//     * @param context
//     * @param permissions
//     * @param callback
//     */
//    public void requestPermissions(final Context context, final String[] permissions,
//                                   RequestPermissionCallBack callback) {
//        this.mRequestPermissionCallBack = callback;
//        StringBuilder permissionNames = new StringBuilder();
//        for(String s : permissions){
//            permissionNames = permissionNames.append(s + "\r\n");
//        }
//        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
//        boolean isAllGranted = true;
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
//                isAllGranted = false;
//
//                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
//                    new AlertDialog.Builder(context)
//                            .setMessage("【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】\r\n" +
//                                    "您好，需要如下权限：\r\n" +
//                                    permissionNames+
//                                    " 请允许，否则将影响部分功能的正常使用。")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
//                                }
//                            }).show();
//                } else {
//                    ActivityCompat.requestPermissions(((Activity) context), permissions, mRequestCode);
//                }
//
//                break;
//            }
//        }
//        if (isAllGranted) {
//            mRequestPermissionCallBack.granted();
//            return;
//        }
//    }
//
//    /**
//     * 权限请求结果回调接口
//     */
//    public interface RequestPermissionCallBack {
//        /**
//         * 同意授权
//         */
//        public void granted();
//
//        /**
//         * 取消授权
//         */
//        public void denied();
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        XPermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }

}
