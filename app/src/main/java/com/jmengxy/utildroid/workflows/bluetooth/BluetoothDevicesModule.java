package com.jmengxy.utildroid.workflows.bluetooth;

import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.utils.bluetooth.BluetoothHoster;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 04/02/2018.
 */

@Module
class BluetoothDevicesModule {
    private final BluetoothDevicesContract.View view;

    public BluetoothDevicesModule(BluetoothDevicesContract.View view) {
        this.view = view;
    }

    @Provides
    BluetoothDevicesContract.Presenter providePresenter(DataSource dataSource, SchedulerProvider schedulerProvider, BluetoothHoster bluetoothHoster) {
        return new BluetoothDevicesPresenter(view, dataSource, schedulerProvider, bluetoothHoster);
    }
}