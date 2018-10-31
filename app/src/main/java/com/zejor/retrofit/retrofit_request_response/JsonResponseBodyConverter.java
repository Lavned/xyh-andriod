package com.zejor.retrofit.retrofit_request_response;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.zejor.encrypt_utils.DesUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;


public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson mGson;//gson对象
    private final TypeAdapter<T> adapter;
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    /**
     * 构造器
     */
    public JsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.mGson = gson;
        this.adapter = adapter;
    }

    /**
     * 转换
     *
     * @param responseBody
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String string = responseBody.string();
//        Logger.e( "convert: 未解密响应 response == " + string );
        try {
            String decrypt = DesUtils.decrypt(string);
//            Logger.e("convert: 解密响应 response == " + decrypt);
            ResponseBody body = ResponseBody.create(MEDIA_TYPE, decrypt);
            JsonReader jsonReader = mGson.newJsonReader(body.charStream());

            return adapter.read(jsonReader);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
