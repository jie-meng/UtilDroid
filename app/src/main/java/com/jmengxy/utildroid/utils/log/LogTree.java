package com.jmengxy.utildroid.utils.log;

import android.util.Log;

import com.jmengxy.utildroid.BuildConfig;

import timber.log.Timber;

/**
 * Created by jiemeng on 20/02/2018.
 */

public class LogTree extends Timber.DebugTree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (BuildConfig.FLAVOR.equals("prod")) {
            return;
        }

        if (BuildConfig.DEBUG || Log.isLoggable("UtilDroid", Log.DEBUG)) {
            super.log(priority, tag, message, t);
            if (Log.isLoggable("UtilDroidSout", Log.DEBUG)) {
                System.out.println(String.format("[%s] %s", tag, message));
            }
        }
    }
}
