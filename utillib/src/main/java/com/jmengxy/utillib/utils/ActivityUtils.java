package com.jmengxy.utillib.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jiemeng on 10/08/2017.
 */

public class ActivityUtils {

    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentAndAddToBackStack(@NonNull FragmentManager fragmentManager,
                                                  @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void replaceFragmentAndAddToBackStack(@NonNull FragmentManager fragmentManager,
                                                      @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
