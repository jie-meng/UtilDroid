package com.jmengxy.utillib.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class FileUtils {
    public static Single<String> readSDCardFile(final String fileName) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                File sdcard = Environment.getExternalStorageDirectory();
                File file = new File(sdcard, fileName);
                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }

                e.onSuccess(text.toString());
            }
        });
    }

    public static Single<Object> writeSDCardFile(final String filename, final String text) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> e) throws Exception {
                File sdcard = Environment.getExternalStorageDirectory();
                FileOutputStream os = new FileOutputStream(new File(sdcard, filename));
                os.write(text.getBytes());
                os.close();

                e.onSuccess(new Object());
            }
        });
    }
}
