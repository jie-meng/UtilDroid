package com.jmengxy.utildroid.workflows.profile.preference;

import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by jiemeng on 16/09/2017.
 */

public class ProfileReferencePresenter implements ProfileReferenceContract.Presenter {

    private final ProfileReferenceContract.View view;
    private final DataSource dataSource;
    private final SchedulerProvider schedulerProvider;
    private final AccountHoster accountHoster;
    private final CompositeDisposable compositeDisposable;

    public ProfileReferencePresenter(ProfileReferenceContract.View view, DataSource dataSource, SchedulerProvider schedulerProvider, AccountHoster accountHoster) {
        this.view = view;
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
        this.accountHoster = accountHoster;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attach() {
        if (accountHoster.isLoggedIn()) {
            view.showProfile(accountHoster.getAccount());
        }
    }

    @Override
    public void detach() {
        compositeDisposable.clear();
    }

    @Override
    public void editUsername() {
        view.showEditUsernameDialog(accountHoster.getAccount());
    }

    @Override
    public void editPassword() {
        view.showEditPasswordDialog(accountHoster.getAccount());
    }

    @Override
    public void editMobileNumber() {
        view.showEditMobileNumberDialog(accountHoster.getAccount());
    }

    @Override
    public void editEmail() {
        view.showEditEmailDialog(accountHoster.getAccount());
    }

    @Override
    public void editNickname() {
        view.showEditNicknameDialog(accountHoster.getAccount());
    }

    @Override
    public void updateProfile(UserEntity userEntity) {
        accountHoster.updateAccount(userEntity);
        view.showProfile(userEntity);
    }
}
