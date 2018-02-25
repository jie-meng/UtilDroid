package com.jmengxy.utildroid.utils.bluetooth;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 22/02/2018.
 */

@Module
public class BluetoothModule {

    @Provides
    @Singleton
    BluetoothHoster provideBluetoothHoster(Context context) {
        return new BluetoothHoster(context);
    }
}
