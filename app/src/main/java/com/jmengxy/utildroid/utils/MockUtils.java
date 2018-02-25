package com.jmengxy.utildroid.utils;

import com.jmengxy.utildroid.BuildConfig;
import com.jmengxy.utildroid.utils.bluetooth.BluetoothHoster;
import com.jmengxy.utildroid.utils.bluetooth.BtDevice;

import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Created by jiemeng on 22/02/2018.
 */

public class MockUtils {

    public static boolean isMock() {
        return BuildConfig.DEBUG && BuildConfig.FLAVOR.equals("mock");
    }

    public static boolean isAutoTest() {
        try {
            Class.forName("android.support.test.espresso.Espresso");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void mockBluetoothHoster(BluetoothHoster mockBluetoothHoster) {
        Mockito.when(mockBluetoothHoster.discoverDevices()).thenAnswer(invocation ->
                Observable.just(new BtDevice("11:22:33:44:55:66", "microphone"),
                        new BtDevice("99:88:77:66:L55:44", "voicebox"))
                        .delay(500, TimeUnit.MILLISECONDS));
    }
}
