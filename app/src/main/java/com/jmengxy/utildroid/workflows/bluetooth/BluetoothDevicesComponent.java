package com.jmengxy.utildroid.workflows.bluetooth;

import com.jmengxy.utildroid.app.AppComponent;
import com.jmengxy.utildroid.workflows.game.list.GameListFragment;
import com.jmengxy.utillib.architecture.dagger.ActivityScoped;
import com.jmengxy.utillib.architecture.dagger.FragmentScoped;

import dagger.Component;

/**
 * Created by jiemeng on 04/02/2018.
 */
@ActivityScoped
@Component(dependencies = AppComponent.class, modules = {BluetoothDevicesModule.class})
public interface BluetoothDevicesComponent {
    void inject(BluetoothDevicesActivity bluetoothDevicesActivity);
}