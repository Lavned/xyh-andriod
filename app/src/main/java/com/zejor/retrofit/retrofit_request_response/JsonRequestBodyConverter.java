package com.zejor.retrofit.retrofit_request_response;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.zejor.encrypt_utils.DesUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by fangwei on 2017/10/11.
 */

public class JsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    /**
     * 构造器
     */

    public JsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }


    @Override
    public RequestBody convert(T value) throws IOException {

        String encrypt = null;
        try {
             encrypt = DesUtils.encrypt(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return RequestBody.create(MEDIA_TYPE, encrypt);
    }

}