package com.jmengxy.utildroid.data.source.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utillib.architecture.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Module
public class RemoteDataSourceModule {

    private static final int TIME_OUT = 15;

    private String baseUrl;

    public RemoteDataSourceModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private static Gson gson;

    public static Gson getGson() {
        return gson;
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gson = gsonBuilder.create();
        return gson;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(UtilDroidGsonConverterFactory.create(GsonConverterFactory.create(gson), gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(RxJava2CallAdapterFactory.create(), gson))
                .build();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(final AccountHoster accountHoster, final Gson gson) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.v(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(new RefreshTokenInterceptor(accountHoster, gson, baseUrl))
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public RemoteDataSource provideRemoteDataSource(Retrofit retrofit, EventBus eventBus) {
        return new RemoteDataSource(retrofit, eventBus);
    }
}
