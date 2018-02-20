package com.jmengxy.utildroid.workflows.profile.preference;

import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 16/09/2017.
 */

@Module
public class ProfileReferenceModule {

    private final ProfileReferenceContract.View view;

    public ProfileReferenceModule(ProfileReferenceContract.View view) {
        this.view = view;
    }

    @Provides
    ProfileReferenceContract.Presenter providePresenter(DataSource dataSource, SchedulerProvider schedulerProvider, AccountHoster accountHoster) {
        return new ProfileReferencePresenter(view, dataSource, schedulerProvider, accountHoster);
    }
}
