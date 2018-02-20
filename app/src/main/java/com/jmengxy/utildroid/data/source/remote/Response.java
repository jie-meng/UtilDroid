package com.jmengxy.utildroid.data.source.remote;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiemeng on 20/02/2018.
 */

class Response<D> {
    @SerializedName("code")
    String code;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    D data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}

