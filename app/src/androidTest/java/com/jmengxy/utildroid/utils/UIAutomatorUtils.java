package com.jmengxy.utildroid.utils;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.v7.widget.RecyclerView;

import com.jmengxy.utildroid.R;
import com.jmengxy.utillib.BuildConfig;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class UIAutomatorUtils {

    private static final long LAUNCH_TIMEOUT = 3000;

    public static void pressHomeAndRestartAppFromLauncher(Context context, String appName, int backgroundDuration) {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        device.pressHome();

        // Wait for launcher
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        SystemClock.sleep(backgroundDuration);

        UiObject appsBUtton = device.findObject(new UiSelector().description("Apps"));
        try {
            appsBUtton.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Scroll to app
        UiScrollable appsListView = new UiScrollable(new UiSelector()
                .resourceId("com.android.launcher3:id/apps_list_view")
                .className(RecyclerView.class));
        try {
            appsListView.scrollTextIntoView(appName);
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            return;
        }

        UiObject app = device.findObject(new UiSelector().description(appName));
        try {
            app.click();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Wait for app
        device.wait(Until.hasObject(By.pkg(BuildConfig.APPLICATION_ID).depth(0)), LAUNCH_TIMEOUT);
    }

    public static void pressHomeAndRestartAppFromLauncher(Context context) {
        pressHomeAndRestartAppFromLauncher(context, context.getString(R.string.app_name), 0);
    }
}
