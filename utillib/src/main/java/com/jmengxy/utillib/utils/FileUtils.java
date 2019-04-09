package com.jmengxy.utillib.utils;

import android.content.Context;
import android.os.Environment;

import com.jmengxy.utillib.functors.Action1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class FileUtils {

    public static Single<String> readFile(final String pathName) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) {
                try {
                    File file = new File(pathName);
                    StringBuilder text = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }

                    e.onSuccess(text.toString().substring(0, text.length() - 1));
                } catch (IOException e1) {
                    e.onError(e1);
                }
            }
        });
    }

    public static Single<Object> writeFile(final String pathName, final String text) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> e) {
                try {
                    FileOutputStream os = new FileOutputStream(new File(pathName));
                    os.write(text.getBytes());
                    os.close();

                    e.onSuccess(new Object());
                } catch (IOException e1) {
                    e.onError(e1);
                }
            }
        });
    }

    public static Single<String> readAssetsFile(final Context context, final String fileName) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
                    StringBuilder text = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }

                    e.onSuccess(text.toString().substring(0, text.length() - 1));
                } catch (IOException e1) {
                    e.onError(e1);
                }
            }
        });
    }

    public static Single<String> readSDCardFile(final String fileName) {
        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) {
                try {
                    File sdcard = Environment.getExternalStorageDirectory();
                    File file = new File(sdcard, fileName);
                    StringBuilder text = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    e.onSuccess(text.toString().substring(0, text.length() - 1));

                } catch (IOException e1) {
                    e.onError(e1);
                }
            }
        });
    }

    public static Single<Object> writeSDCardFile(final String filename, final String text) {
        return Single.create(new SingleOnSubscribe<Object>() {
            @Override
            public void subscribe(SingleEmitter<Object> e) {
                try {
                    File sdcard = Environment.getExternalStorageDirectory();
                    FileOutputStream os = new FileOutputStream(new File(sdcard, filename));
                    os.write(text.getBytes());
                    os.close();
                    e.onSuccess(new Object());
                } catch (IOException e1) {
                    e.onError(e1);
                }
            }
        });
    }

    public static void walk(String path, Action1<File> action, boolean recursive) {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) return;

        for (File f : list) {
            if (f.isDirectory()) {
                if (recursive) {
                    walk(f.getAbsolutePath(), action, recursive);
                }
                action.apply(f.getAbsoluteFile());
            } else {
                action.apply(f.getAbsoluteFile());
            }
        }
    }
}
