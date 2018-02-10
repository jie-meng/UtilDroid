package com.jmengxy.utildroid.data.source;

import com.jmengxy.utildroid.data.source.local.LocalDataSourceModule;
import com.jmengxy.utildroid.data.source.remote.RemoteDataSourceModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Module(includes = {RemoteDataSourceModule.class, LocalDataSourceModule.class})
public class DataSourceModule {
    @Provides
    @Singleton
    DataSource provideDataSource(DataRepository dataRepository) {
        return dataRepository;
    }
}
