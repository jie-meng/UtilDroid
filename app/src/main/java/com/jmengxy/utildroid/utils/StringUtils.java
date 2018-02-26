package com.jmengxy.utildroid.utils;

import android.text.TextUtils;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class StringUtils {

    public static boolean checkPassword(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 8) {
            return false;
        }

        boolean hasAlpha = false;
        boolean hasDigit = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isAlphabetic(password.charAt(i))) {
                hasAlpha = true;
            }

            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            }
        }

        return hasAlpha && hasDigit;
    }
}
