package com.jmengxy.utildroid.data.source.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.models.RefreshTokenRequest;
import com.jmengxy.utildroid.models.RefreshTokenResponse;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by jiemeng on 20/02/2018.
 */

public class RefreshTokenInterceptor implements Interceptor {

    private static final String HTTP_HEADER_ACCEPT = "Accept";
    private static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    private static final String HTTP_HEADER_AUTHORIZATION = "Auth-Token";
    private static final String HTTP_HEADER_CLIENT_ID = "Client-ID";

    private AccountHoster accountHoster;
    private Gson gson;
    private String baseUrl;

    public RefreshTokenInterceptor(AccountHoster accountHoster, Gson gson, String baseUrl) {
        this.accountHoster = accountHoster;
        this.gson = gson;
        this.baseUrl = baseUrl;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = buildRequest(accountHoster, chain);
        okhttp3.Response response = chain.proceed(request);
        if (response.code() != HttpResponseCode.UNAUTHORIZED || !accountHoster.isLoggedIn()) {
            return response;
        }

        okhttp3.Response refreshTokenResponse = refreshToken(accountHoster, gson, chain);
        if (refreshTokenResponse.code() == HttpResponseCode.OK) {
            Type type = new TypeToken<com.jmengxy.utildroid.data.source.remote.Response<RefreshTokenResponse>>() {
            }.getType();
            String accessToken = ((com.jmengxy.utildroid.data.source.remote.Response<RefreshTokenResponse>) gson.fromJson(refreshTokenResponse.body().string(), type))
                    .getData().getAccessToken();
            accountHoster.updateAccountAccessToken(accessToken);
            request = buildRequest(accountHoster, chain);
            return chain.proceed(request);
        } else {
            return refreshTokenResponse;
        }
    }

    private okhttp3.Response refreshToken(AccountHoster accountHoster, Gson gson, Interceptor.Chain chain) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/v1/user/refresh_token")
                .addHeader(HTTP_HEADER_CONTENT_TYPE, "application/json")
                .addHeader(HTTP_HEADER_ACCEPT, "application/json")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        gson.toJson(new RefreshTokenRequest(accountHoster.getAuthToken(), this.accountHoster.getClientId(), accountHoster.getRefreshToken()))))
                .build();

        return chain.proceed(request);
    }

    private Request buildRequest(AccountHoster accountHoster, Interceptor.Chain chain) {
        String authToken = accountHoster.getAuthToken();
        String clientId = accountHoster.getClientId();
        Request request = chain.request().newBuilder()
                .addHeader(HTTP_HEADER_CONTENT_TYPE, "application/json")
                .addHeader(HTTP_HEADER_ACCEPT, "application/json")
                .addHeader(HTTP_HEADER_AUTHORIZATION, authToken == null ? "" : authToken)
                .addHeader(HTTP_HEADER_CLIENT_ID, clientId == null ? "" : clientId)
                .build();

        return request;
    }
}

