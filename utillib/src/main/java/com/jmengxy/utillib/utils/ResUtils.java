package com.jmengxy.utillib.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiemeng on 26/12/2017.
 */

public class ResUtils {
    public static byte[] readRaw(Context context, int resId) throws IOException {
        try (InputStream inputStream = context.getResources().openRawResource(resId)) {
            byte[] data = new byte[inputStream.available()];
            int total = 0;
            do {
                int read = inputStream.read(data, total, inputStream.available());
                if (read <= 0) {
                    break;
                }
                total += read;
            } while (true);
            return data;
        }
    }
}
