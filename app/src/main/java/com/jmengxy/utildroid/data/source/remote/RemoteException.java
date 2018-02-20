package com.jmengxy.utildroid.data.source.remote;

import com.google.gson.Gson;

/**
 * Created by jiemeng on 20/02/2018.
 */

public class RemoteException extends RuntimeException {

    public static RemoteException httpError(String url, retrofit2.Response response, Throwable cause, Gson gson) {
        return response.code() == HttpResponseCode.UNAUTHORIZED ? new RemoteAuthException(url, response, cause, gson) : new RemoteException(url, response, cause, gson);
    }

    private final String url;
    private final retrofit2.Response response;
    private final int code;
    private final Gson gson;
    private com.jmengxy.utildroid.data.source.remote.Response errorResponse;

    protected RemoteException(String url, retrofit2.Response response, Throwable exception, Gson gson) {
        super(response.message(), exception);
        this.url = url;
        this.response = response;
        this.code = response.code();
        this.gson = gson;
    }

    public String getUrl() {
        return url;
    }

    public com.jmengxy.utildroid.data.source.remote.Response getResponse() {
        if (errorResponse == null) {
            if (response != null && response.errorBody() != null) {
                try {
                    String errorBody = response.errorBody().string();
                    if (errorBody == null) {
                        return null;
                    }
                    errorResponse = gson.fromJson(errorBody, com.jmengxy.utildroid.data.source.remote.Response.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return errorResponse;
    }

    public int getCode() {
        return code;
    }

    public String getErrorCode() {
        return getResponse().code;
    }

    public String getErrorMessage() {
        if (getResponse() != null) {
            return getResponse().getMessage();
        }
        return null;
    }
}
