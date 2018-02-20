package com.jmengxy.utildroid.models;

import com.google.gson.annotations.SerializedName;

public class RefreshTokenResponse {
    @SerializedName("access_token")
    String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
