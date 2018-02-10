package com.jmengxy.utildroid.data.source;

import com.jmengxy.utildroid.models.User;

import io.reactivex.Single;

/**
 * Created by jiemeng on 25/01/2018.
 */

public interface DataSource {

    Single<User> login(User user);
}
