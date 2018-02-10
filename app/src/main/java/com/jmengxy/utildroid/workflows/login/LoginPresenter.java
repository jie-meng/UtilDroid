package com.jmengxy.utildroid.workflows.login;

import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.models.User;
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
    private final User user;
    private final CompositeDisposable compositeDisposable;

    public LoginPresenter(LoginContract.View view, DataSource dataSource, SchedulerProvider schedulerProvider, AccountHoster accountHoster, User user) {
        this.view = view;
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
        this.accountHoster = accountHoster;
        this.user = user;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attach() {
        if (user.getUsername() != null) {
            view.showUsername(user.getUsername());
        }

        if (user.getPassword() != null) {
            view.showPassword(user.getPassword());
        }
    }

    @Override
    public void detach() {
        compositeDisposable.clear();
    }

    @Override
    public void setUsername(String username) {
        user.setUsername(username);
    }

    @Override
    public void setPassword(String password) {
        user.setPassword(password);
    }

    @Override
    public void gotoNext() {
        view.showProgress(true);
        dataSource.login(user)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(new SingleObserver<User>() {
                               @Override
                               public void onSubscribe(Disposable d) {
                                   compositeDisposable.add(d);
                               }

                               @Override
                               public void onSuccess(User o) {
                                   view.showProgress(false);
                                   accountHoster.saveAccount(user, true);
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
