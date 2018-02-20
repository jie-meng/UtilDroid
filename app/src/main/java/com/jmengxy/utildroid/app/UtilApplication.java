package com.jmengxy.utildroid.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.jmengxy.utildroid.BuildConfig;
import com.jmengxy.utildroid.data.source.remote.RemoteDataSourceModule;
import com.jmengxy.utildroid.utils.log.LogTree;

import timber.log.Timber;

/**
 * Created by jiemeng on 10/02/2018.
 */

public class UtilApplication extends Application {
    private AppComponent appComponent;

    public static UtilApplication get(Context context) {
        return (UtilApplication) context.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .remoteDataSourceModule(new RemoteDataSourceModule(BuildConfig.SERVER_URL))
                .build();

        Timber.plant(new LogTree());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
