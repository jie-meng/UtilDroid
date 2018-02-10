package com.jmengxy.utildroid.workflows.login;

import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.models.User;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 04/02/2018.
 */

@Module
class LoginModule {
    private final LoginContract.View view;
    private User user;

    public LoginModule(LoginContract.View view, User user) {
        this.view = view;
        this.user = user;
    }

    @Provides
    LoginContract.Presenter providePresenter(DataSource dataSource, SchedulerProvider schedulerProvider, AccountHoster accountHoster) {
        return new LoginPresenter(view, dataSource, schedulerProvider, accountHoster, user);
    }
}