package com.jmengxy.utildroid.data.source.local;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Module
public class LocalDataSourceModule {

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource() {
        return new LocalDataSource();
    }
}
