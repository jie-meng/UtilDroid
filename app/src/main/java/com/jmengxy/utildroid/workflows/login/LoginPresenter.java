package com.jmengxy.utildroid.workflows.login;

import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiemeng on 04/02/2018.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private final DataSource dataSource;
    private final SchedulerProvider schedulerProvider;
    private final AccountHoster accountHoster;
    private final LoginRequest loginRequest;
    private final CompositeDisposable compositeDisposable;

    public LoginPresenter(LoginContract.View view, DataSource dataSource, SchedulerProvider schedulerProvider, AccountHoster accountHoster, LoginRequest loginRequest) {
        this.view = view;
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
        this.accountHoster = accountHoster;
        this.loginRequest = loginRequest;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attach() {
        if (loginRequest.getUsername() != null) {
            view.showUsername(loginRequest.getUsername());
        }

        if (loginRequest.getPassword() != null) {
            view.showPassword(loginRequest.getPassword());
        }
    }

    @Override
    public void detach() {
        compositeDisposable.clear();
    }

    @Override
    public void setUsername(String username) {
        loginRequest.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        loginRequest.setPassword(password);
    }

    @Override
    public void gotoNext() {
        view.showProgress(true);
        dataSource.login(loginRequest)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<UserEntity>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   compositeDisposable.add(d);
                               }

                               @Override
                               public void onSuccess(UserEntity userEntity) {
                                   view.showProgress(false);
                                   accountHoster.saveAccount(userEntity, true);
                                   view.gotoNextPage();
                               }

                               @Override
                               public void onError(Throwable e) {
                                   view.showProgress(false);
                                   view.showError(0, e.toString());
                               }
                           }
                );
    }
}
