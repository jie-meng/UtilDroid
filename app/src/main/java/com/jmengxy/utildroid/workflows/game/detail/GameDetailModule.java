package com.jmengxy.utildroid.workflows.game.detail;

import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiemeng on 04/02/2018.
 */

@Module
class GameDetailModule {
    private final GameDetailContract.View view;
    private GameEntity gameEntity;

    public GameDetailModule(GameDetailContract.View view, GameEntity gameEntity) {
        this.view = view;
        this.gameEntity = gameEntity;
    }

    @Provides
    GameDetailContract.Presenter providePresenter(DataSource dataSource, SchedulerProvider schedulerProvider) {
        return new GameDetailPresenter(view, dataSource, schedulerProvider, gameEntity);
    }
}