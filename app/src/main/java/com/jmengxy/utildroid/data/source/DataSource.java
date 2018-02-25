package com.jmengxy.utildroid.data.source;

import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utildroid.models.UserEntity;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by jiemeng on 25/01/2018.
 */

public interface DataSource {

    Single<UserEntity> login(LoginRequest loginRequest);

    Single<Object> logout();

    Single<List<GameEntity>> getGames();
}
