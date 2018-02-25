package com.jmengxy.utildroid.utils.bluetooth;

import android.content.Context;

import com.jmengxy.utildroid.utils.MockUtils;

import org.mockito.Mockito;

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
        if (MockUtils.isMock()) {
            BluetoothHoster mockBluetoothHoster = Mockito.mock(BluetoothHoster.class);
            MockUtils.mockBluetoothHoster(mockBluetoothHoster);
            return mockBluetoothHoster;
        } else {
            return new BluetoothHoster(context);
        }
    }
}