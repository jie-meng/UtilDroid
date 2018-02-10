package com.jmengxy.utillib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jiemeng on 26/12/2017.
 */

public class SharedPreferenceUtils {
    public static String readString(Context context, String prefName, String name, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, defaultValue);
    }

    public static void writeString(Context context, String prefName, String name, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(name, value).commit();
    }

    public static boolean readBoolean(Context context, String prefName, String name, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, defaultValue);
    }

    public static void writeBoolean(Context context, String prefName, String name, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(name, value).commit();
    }
}
