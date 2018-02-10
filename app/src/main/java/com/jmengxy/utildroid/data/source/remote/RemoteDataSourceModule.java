package com.jmengxy.utildroid.data.source.remote;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Module
public class RemoteDataSourceModule {

    private static Gson gson;

    public static Gson getGson() {
        return gson;
    }

    @Provides
    @Singleton
    public RemoteDataSource provideRemoteDataSource() {
        return new RemoteDataSource();
    }
}
