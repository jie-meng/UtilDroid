package com.jmengxy.utildroid.utils;

import android.support.test.InstrumentationRegistry;

import com.jmengxy.utildroid.app.UtilApplication;

/**
 * Created by jiemeng on 27/02/2018.
 */

public class UtilDroidUtils {
    public static UtilApplication getApp() {
        return (UtilApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }
}
