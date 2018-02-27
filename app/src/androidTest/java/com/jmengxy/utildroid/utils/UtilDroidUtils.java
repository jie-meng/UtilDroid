package com.jmengxy.utildroid.utils;

import android.support.test.InstrumentationRegistry;

import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.data.source.remote.RemoteException;
import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utildroid.models.UserEntity;

import junit.framework.Assert;

/**
 * Created by jiemeng on 27/02/2018.
 */

public class UtilDroidUtils {
    public static UtilApplication getApp() {
        return (UtilApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }

    public static void login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        UserEntity userEntity = null;
        try {
            userEntity = getApp().getAppComponent().getDataSource()
                    .login(loginRequest)
                    .blockingGet();
        } catch (Exception e) {
            if (e instanceof RemoteException) {
                String errorMsg = ((RemoteException) e).getErrorMessage();
                if (errorMsg != null) {
                    Assert.fail(errorMsg);
                } else {
                    Assert.fail("Unknown Error: " + e.toString());
                }
            } else {
                Assert.fail("Network Error: " + e.toString());
            }
        }

        getApp().getAppComponent().getAccountHoster().saveAccount(userEntity, true);
    }
}
