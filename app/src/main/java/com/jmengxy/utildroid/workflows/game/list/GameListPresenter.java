package com.jmengxy.utildroid.workflows.game.list;

import com.jmengxy.utildroid.data.source.DataSource;
import com.jmengxy.utillib.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiemeng on 04/02/2018.
 */

public class GameListPresenter implements GameListContract.Presenter {

    private final GameListContract.View view;
    private final DataSource dataSource;
    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    public GameListPresenter(GameListContract.View view, DataSource dataSource, SchedulerProvider schedulerProvider) {
        this.view = view;
        this.dataSource = dataSource;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
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
        Disposable disposable = dataSource.getGames()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(gameEntities -> {
                    view.showProgress(false);
                    view.showGames(gameEntities);
                }, throwable -> {
                    view.showProgress(false);
                    view.showError(0, throwable.getMessage());
                });

        compositeDisposable.add(disposable);
    }
}
