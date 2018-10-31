package com.zejor.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/2.
 */
public class SharedPerferenceUtil {

    private static final String fileName = "JD360";

    /**
     * 保存数据到文件
     */
    public static void saveData(Context context, String key, Object value) {
        String type = value.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) value);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) value);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) value);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) value);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    /**
     * 从文件中读取数据
     */
    public static Object getData(Context context, String key, Object defaultValue) {
        String type = defaultValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (fileName, Context.MODE_PRIVATE);

        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defaultValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        }
        return null;
    }


    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

}
