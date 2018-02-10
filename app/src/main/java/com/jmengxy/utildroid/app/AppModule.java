package com.jmengxy.utildroid.app;

import android.app.Application;
import android.content.Context;

import com.jmengxy.utildroid.account_hoster.AccountHoster;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    public AccountHoster provideAccountHoster(Context context) {
        return new AccountHoster(context);
    }
}
