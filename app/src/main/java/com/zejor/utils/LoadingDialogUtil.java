package com.zejor.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.zejor.App;

/**
 * 加载中的进度框
 */

public class LoadingDialogUtil {


    private static LoadingDialogUtil loadingDialogUtil;
    private ProgressDialog progressDialog;

    private LoadingDialogUtil() {

    }

    public static LoadingDialogUtil getInstance() {
        if (loadingDialogUtil == null) {
            loadingDialogUtil = new LoadingDialogUtil();
        }
        return loadingDialogUtil;
    }

    public boolean isShowing() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(App.getInstance());
        }
        return progressDialog.isShowing();
    }

    public void showDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("加载中,请稍候");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public void showDialog1(Context context,String text) {
        progressDialog.setCancelable(false);
        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(text);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                    System.out.println("按下了back键   onKeyDown()");
                    return true;
                }
                return false;
            }
        });
    }
    public void stopDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
