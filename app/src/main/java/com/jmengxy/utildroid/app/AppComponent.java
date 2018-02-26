package com.jmengxy.utildroid.app;

import android.content.Context;

import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.data.source.DataSourceModule;
import com.jmengxy.utildroid.schedulers.SchedulerProviderModule;
import com.jmengxy.utildroid.utils.bluetooth.BluetoothHoster;
import com.jmengxy.utildroid.utils.bluetooth.BluetoothModule;
import com.jmengxy.utildroid.utils.eventbus.EventBusModule;
import com.jmengxy.utillib.architecture.eventbus.EventBus;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Component(modules = {AppModule.class, EventBusModule.class, BluetoothModule.class, DataSourceModule.class, SchedulerProviderModule.class})
@Singleton
public interface AppComponent {
    Context getAppContext();

    EventBus getEventBus();

    OkHttpClient getOkHttpClient();

    BluetoothHoster getBluetoothHoster();

    DataSource getDataSource();

    AccountHoster getAccountHoster();

    SchedulerProvider getSchedulerProvider();
}
