package com.zejor.encrypt_utils;


import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class ParameterEncryptionUtil {

    private static ParameterEncryptionUtil instance;

    private ParameterEncryptionUtil() {

    }

    public static ParameterEncryptionUtil getInstance() {
        if (null == instance) {
            instance = new ParameterEncryptionUtil();
        }
        return instance;
    }


    /**
     * 将穿入的集合转化为加密的body
     *
     * @param map 需要转化的参数集合
     * @return 加密过后的body对象
     */
    public RequestBody  getRequestBody(Map map) {
        RequestBody body = null;

        Gson gson = new Gson();
        String json = gson.toJson(map);
        Logger.e(json);
        String encString = null;
        try {
            body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

}
