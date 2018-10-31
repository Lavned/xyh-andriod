package com.zejor.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.zejor.App;

public class ToastUtils {

    private static Toast toast;

    public static void showToast(Context context,
        String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                         content, 
                         Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 使用appContext弹吐司
     * @param content
     */
    public static void showToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(),
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}