package com.jmengxy.utillib.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

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

    public static int readInt(Context context, String prefName, String name, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(name, defaultValue);
    }

    public static void writeInt(Context context, String prefName, String name, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(name, value).commit();
    }

    public static long readLong(Context context, String prefName, String name, long defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(name, defaultValue);
    }

    public static void writeLong(Context context, String prefName, String name, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putLong(name, value).commit();
    }

    public static float readDouble(Context context, String prefName, String name, float defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(name, defaultValue);
    }

    public static void writeFloat(Context context, String prefName, String name, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat(name, value).commit();
    }

    public static Set<String> readStringSet(Context context, String prefName, String name, Set<String> defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(name, defaultValue);
    }

    public static void writeStringSet(Context context, String prefName, String name, Set<String> value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        sharedPreferences.edit().putStringSet(name, value).commit();
    }
}
