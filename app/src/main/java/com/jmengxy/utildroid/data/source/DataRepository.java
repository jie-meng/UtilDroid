package com.jmengxy.utildroid.data.source;

import android.support.annotation.NonNull;


import com.jmengxy.utildroid.data.source.local.LocalDataSource;
import com.jmengxy.utildroid.data.source.remote.RemoteDataSource;
import com.jmengxy.utildroid.models.User;

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
    public Single<User> login(User user) {
        return remoteDataSource.login(user);
    }
}
