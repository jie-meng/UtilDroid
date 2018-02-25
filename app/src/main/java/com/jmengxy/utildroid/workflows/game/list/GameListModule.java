package com.jmengxy.utildroid.workflows.game.list;

import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 04/02/2018.
 */

@Module
class GameListModule {
    private final GameListContract.View view;

    public GameListModule(GameListContract.View view) {
        this.view = view;
    }

    @Provides
    GameListContract.Presenter providePresenter(DataSource dataSource, SchedulerProvider schedulerProvider) {
        return new GameListPresenter(view, dataSource, schedulerProvider);
    }
}