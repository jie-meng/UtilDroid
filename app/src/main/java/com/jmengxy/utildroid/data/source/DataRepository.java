package com.jmengxy.utildroid.data.source;

import android.support.annotation.NonNull;

import com.jmengxy.utildroid.data.source.local.LocalDataSource;
import com.jmengxy.utildroid.data.source.remote.RemoteDataSource;
import com.jmengxy.utildroid.models.GameCommentEntity;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utildroid.models.UserEntity;

import java.util.List;

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

    @Override
    public Single<List<GameEntity>> getGames() {
        return remoteDataSource.getGames();
    }

    @Override
    public Single<GameEntity> getGame(String gameId) {
        return remoteDataSource.getGame(gameId);
    }

    @Override
    public Single<List<GameCommentEntity>> getGameComments(String gameId) {
        return remoteDataSource.getGameComments(gameId);
    }

    @Override
    public Single<Object> clearData() {
        return Single.create(e -> {
            localDataSource.clearAllData();
            e.onSuccess(new Object());
        });
    }
}
