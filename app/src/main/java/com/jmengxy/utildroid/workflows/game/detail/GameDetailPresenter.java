package com.jmengxy.utildroid.workflows.game.detail;

import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiemeng on 04/02/2018.
 */

public class GameDetailPresenter implements GameDetailContract.Presenter {

    private final GameDetailContract.View view;
    private final DataSource dataSource;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;
    private GameEntity gameEntity;

    public GameDetailPresenter(GameDetailContract.View view, DataSource dataSource, SchedulerProvider schedulerProvider, GameEntity gameEntity) {
        this.view = view;
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();
        this.gameEntity = gameEntity;
    }

    @Override
    public void attach() {
        refresh();
    }

    @Override
    public void detach() {
        compositeDisposable.clear();
    }

    @Override
    public void refresh() {
        Disposable disposable = dataSource.getGame(gameEntity.getId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(gameEntities -> {
                    view.showProgress(false);
                    view.showGame(gameEntities);
                }, throwable -> {
                    view.showProgress(false);
                    view.showError(0, throwable.getMessage());
                });

        compositeDisposable.add(disposable);

        disposable = dataSource.getGameComments(gameEntity.getId())
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(gameCommentEntities -> {
                    view.showComments(gameCommentEntities);
                }, throwable -> {
                    view.showRetry();
                });

        compositeDisposable.add(disposable);
    }
}
