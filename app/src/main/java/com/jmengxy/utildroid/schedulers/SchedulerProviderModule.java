package com.jmengxy.utildroid.schedulers;

import com.jmengxy.utillib.schedulers.AndroidSchedulerProvider;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 28/08/2017.
 */

@Module
public class SchedulerProviderModule {

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider() {
        return new AndroidSchedulerProvider();
    }
}
