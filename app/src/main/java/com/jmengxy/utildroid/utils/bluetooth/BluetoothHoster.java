package com.jmengxy.utildroid.utils.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class BluetoothHoster {

    private final Context context;

    private final BluetoothAdapter bluetoothAdapter;

    private final SingBroadcastReceiver singBroadcastReceiver;

    private PublishSubject<BtDevice> subject = PublishSubject.create();

    private boolean isRegistered;

    public BluetoothHoster(Context context) {
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        singBroadcastReceiver = new SingBroadcastReceiver();
    }

    public Observable<BtDevice> discoverDevices() {
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            registerBroadcastReceiver();
            unregisterBroadcastReceiver();

            if (bluetoothAdapter.isDiscovering()) {
                bluetoothAdapter.cancelDiscovery();
            }

            bluetoothAdapter.startDiscovery();

            return subject.doOnDispose(() -> {
                bluetoothAdapter.cancelDiscovery();
                unregisterBroadcastReceiver();
            });
        } else {
            return Observable.error(new Exception("Bluetooth not enabled"));
        }
    }

    private class SingBroadcastReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                subject.onNext(new BtDevice(device.getAddress(), device.getName()));
            }
        }
    }

    private void registerBroadcastReceiver() {
        if (isRegistered) {
            return;
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(singBroadcastReceiver, filter);
        isRegistered = true;
    }

    private void unregisterBroadcastReceiver() {
        if (!isRegistered) {
            return;
        }

        context.unregisterReceiver(singBroadcastReceiver);
        isRegistered = false;
    }
}
