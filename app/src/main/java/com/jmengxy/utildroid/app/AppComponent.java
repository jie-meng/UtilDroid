package com.jmengxy.utildroid.app;

import android.content.Context;

import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.data.source.DataSourceModule;
import com.jmengxy.utildroid.schedulers.SchedulerProviderModule;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Component(modules = {AppModule.class, DataSourceModule.class, SchedulerProviderModule.class})
@Singleton
public interface AppComponent {
    Context getAppContext();

    DataSource getDataSource();

    AccountHoster getAccountHoster();

    SchedulerProvider getSchedulerProvider();
}
