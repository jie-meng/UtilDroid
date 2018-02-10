package com.jmengxy.utillib.utils;

import static android.os.Build.MANUFACTURER;
import static android.os.Build.MODEL;

/**
 * Created by jiemeng on 26/12/2017.
 */

public class DeviceUtils {
    public static String getDeviceName() {
        String manufacturer = MANUFACTURER;
        String model = MODEL;
        if (model.startsWith(manufacturer)) {
            return StringUtils.capitalize(model);
        }

        return StringUtils.capitalize(manufacturer) + " " + model;
    }
}
