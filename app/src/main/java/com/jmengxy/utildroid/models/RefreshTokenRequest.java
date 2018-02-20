package com.jmengxy.utildroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jiemeng on 20/02/2018.
 */

public class RefreshTokenRequest {
    @SerializedName("access_token")
    String accessToken;

    @SerializedName("client_id")
    String clientId;

    @SerializedName("refresh_token")
    String refreshToken;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String accessToken, String clientId, String refreshToken) {
        this.accessToken = accessToken;
        this.clientId = clientId;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
