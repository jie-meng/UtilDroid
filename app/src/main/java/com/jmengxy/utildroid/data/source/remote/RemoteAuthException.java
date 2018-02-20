package com.jmengxy.utildroid.data.source.remote;

import com.google.gson.Gson;

/**
 * Created by jiemeng on 20/02/2018.
 */

public class RemoteAuthException extends RemoteException {

    protected RemoteAuthException(String url, retrofit2.Response response, Throwable exception, Gson gson) {
        super(url, response, exception, gson);
    }
}
