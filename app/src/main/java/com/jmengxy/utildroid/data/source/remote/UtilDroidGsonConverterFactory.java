package com.jmengxy.utildroid.data.source.remote;

import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URLEncoder;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiemeng on 20/02/2018.
 */

public class UtilDroidGsonConverterFactory extends Converter.Factory {
    public static UtilDroidGsonConverterFactory create(GsonConverterFactory gsonConverterFactory, Gson gson) {
        if (gsonConverterFactory == null)
            throw new NullPointerException("gsonConverterFactory == null");
        if (gson == null) throw new NullPointerException("gson == null");
        return new UtilDroidGsonConverterFactory(gsonConverterFactory, gson);
    }

    private final GsonConverterFactory gsonConverterFactory;
    private final Gson gson;


    private UtilDroidGsonConverterFactory(GsonConverterFactory gsonConverterFactory, Gson gson) {
        this.gsonConverterFactory = gsonConverterFactory;
        this.gson = gson;
    }

    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return (Converter<Object, String>) value -> {
            if (value instanceof CharSequence || value instanceof Boolean || value instanceof Character || value instanceof Number)
                return value.toString();
            return URLEncoder.encode(gson.toJson(value), "UTF-8");
        };
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return gsonConverterFactory.responseBodyConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return gsonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }
}
