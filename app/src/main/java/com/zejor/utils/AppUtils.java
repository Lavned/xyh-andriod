package com.zejor.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.zejor.App;

/**
 * 工具
 * Created by Administrator on 2017/8/10.
 */

public class AppUtils {

    /**
     * 获取版本名
     *
     * @return 获取不到位空字符串
     */
    public static String getAppVersionName() {
        try {
            PackageManager manager = App.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(), 0);
            String version = info.versionName;
            return version + "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 检测所有输入框是否有内容
     * @param view  需要做处理的空间
     * @param list  一个或多个输入框
     */
//    public static void checkString(View view, EditText... list){
//        for (EditText et:list){
//            String s = et.getText().toString().trim();
//            if(TextUtils.isEmpty(s)){
//                view.setEnabled(false);
//                view.setAlpha(0.3f);
//                return;
//            }
//        }
//        view.setEnabled(true);
//        view.setAlpha(1f);
//    }

    /*复制文案*/
//    public static void copyString(String content, Context context) {
//        // 得到剪贴板管理器
//        ClipboardManager cmb = (ClipboardManager) context
//                .getSystemService(Context.CLIPBOARD_SERVICE);
//        cmb.setText(content.trim());
//    }


    /**
     * 按钮倒计时效果
     */
    public static void tvSendCode(final TextView btnGetCheckCode) {
        //发送验证码成功才能倒计时
        btnGetCheckCode.setClickable(false);
        btnGetCheckCode.setEnabled(false);
        CountDownTimer tm = new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btnGetCheckCode.setText(millisUntilFinished / 1000 + " 秒");
            }

            @Override
            public void onFinish() {
                btnGetCheckCode.setClickable(true);
                btnGetCheckCode.setEnabled(true);
                btnGetCheckCode.setText("重新发送");

            }
        };
        tm.start();
    }

}
