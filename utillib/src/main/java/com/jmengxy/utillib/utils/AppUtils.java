package com.jmengxy.utillib.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.List;

/**
 * Created by jiemeng on 10/02/2018.
 */

public class AppUtils {
    public static void restart(Context context, String applicationId) {
        killBackgroundProcesses(context, applicationId);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                super.run();
                PackageManager packageManager = context.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                ComponentName componentName = intent.getComponent();
                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                context.getApplicationContext().startActivity(mainIntent);

            }
        });
        Runtime.getRuntime().exit(0);
    }

    public static void suicide(Context context, String applicationId) {
        killBackgroundProcesses(context, applicationId);
        Runtime.getRuntime().exit(0);
    }

    public static boolean isPackageInstalled(Context context, String packagename) {
        try {
            context.getPackageManager().getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void startApplication(Context context, String packageName) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
    }

    private static void killBackgroundProcesses(Context context, String applicationId) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = manager
                .getRunningAppProcesses();
        if (runningProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (!applicationId.equalsIgnoreCase(processInfo.processName)) {
                    android.os.Process.killProcess(processInfo.pid);
                }
            }
        }
    }
}
