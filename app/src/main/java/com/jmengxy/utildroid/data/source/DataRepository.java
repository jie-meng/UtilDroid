package com.jmengxy.utildroid.data.source;

import android.support.annotation.NonNull;

import com.jmengxy.utildroid.data.source.local.LocalDataSource;
import com.jmengxy.utildroid.data.source.remote.RemoteDataSource;
import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utildroid.models.UserEntity;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by jiemeng on 25/01/2018.
 */

public class DataRepository implements DataSource {

    @NonNull
    private final RemoteDataSource remoteDataSource;

    @NonNull
    private final LocalDataSource localDataSource;

    @Inject
    public DataRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Single<UserEntity> login(LoginRequest loginRequest) {
        return remoteDataSource.login(loginRequest);
    }

    @Override
    public Single<Object> logout() {
        return remoteDataSource
                .logout()
                .doFinally(() -> {
                    localDataSource.clearAllData();
                });
    }
}
