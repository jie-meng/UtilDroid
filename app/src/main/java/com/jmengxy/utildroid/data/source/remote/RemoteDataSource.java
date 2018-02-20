package com.jmengxy.utildroid.data.source.remote;


import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utildroid.utils.eventbus.EventBus;

import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.jmengxy.utildroid.utils.eventbus.RemoteEvent.authTokenInvalidEvent;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Singleton
public class RemoteDataSource {

    private interface UserApi {
        @POST("user/login")
        Single<Response<UserEntity>> login(@Body LoginRequest loginRequest);
    }

    private final EventBus eventBus;
    private final UserApi userApi;

    public RemoteDataSource(Retrofit retrofit, EventBus eventBus) {
        this.eventBus = eventBus;
        this.userApi = retrofit.create(UserApi.class);
    }

    public Single<UserEntity> login(LoginRequest loginRequest) {
        return userApi
                .login(loginRequest)
                .compose(unwrap(UserEntity.class));
    }

    private <T> SingleTransformer<Response<T>, Response<T>> checkAuthFailure(Class<T> clazz) {
        return upstream -> upstream.doOnError((Consumer<Throwable>) throwable -> {
            if (throwable instanceof RemoteAuthException) {
                RemoteAuthException authException = (RemoteAuthException) throwable;
                eventBus.post(authTokenInvalidEvent(authException.getErrorMessage()));
            }
        });
    }

    private <T> SingleTransformer<Response<T>, T> unwrap(final Class<T> clazz) {
        return upstream -> upstream
                .map((Function<Response<T>, T>) response -> {
                    T data = response.getData();
                    if (data != null) {
                        return data;
                    } else {
                        return clazz.newInstance();
                    }
                });
    }
}
