package com.jmengxy.utildroid.data.source.remote;


import com.jmengxy.utildroid.models.User;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by jiemeng on 25/01/2018.
 */

@Singleton
public class RemoteDataSource {

    public Single<User> login(User user) {
        User retUser = new User();
        retUser.setUsername(user.getUsername());
        retUser.setNickname("Josh");
        retUser.setMobileNumber("12345678");
        retUser.setEmail("abc@gmail.com");
        retUser.setAccessToken(UUID.randomUUID().toString());
        return Single.just(retUser).delay(2000, TimeUnit.MILLISECONDS);
    }
}
