package com.jmengxy.utildroid.workflows.bluetooth;

import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.utils.bluetooth.BluetoothHoster;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiemeng on 04/02/2018.
 */

public class BluetoothDevicesPresenter implements BluetoothDevicesContract.Presenter {

    private final BluetoothDevicesContract.View view;
    private final DataSource dataSource;
    private final SchedulerProvider schedulerProvider;
    private final BluetoothHoster bluetoothHoster;
    private final CompositeDisposable compositeDisposable;

    public BluetoothDevicesPresenter(BluetoothDevicesContract.View view, DataSource dataSource, SchedulerProvider schedulerProvider, BluetoothHoster bluetoothHoster) {
        this.view = view;
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
        this.bluetoothHoster = bluetoothHoster;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attach() {
        scan();
    }

    @Override
    public void detach() {
        compositeDisposable.clear();
    }

    @Override
    public void scan() {
        view.showProgress(true);
        Disposable subscribe = bluetoothHoster.discoverDevices()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(btDevice -> {
                    view.showProgress(false);
                    view.foundNewDevice(btDevice);
                }, throwable -> {
                    view.showProgress(false);
                    view.showError(0, throwable.getMessage());
                });
        compositeDisposable.add(subscribe);
    }
}
