package com.jmengxy.utildroid.workflows.game.list;

import com.jmengxy.utildroid.app.AppComponent;
import com.jmengxy.utillib.architecture.dagger.FragmentScoped;

import dagger.Component;

/**
 * Created by jiemeng on 04/02/2018.
 */
@FragmentScoped
@Component(dependencies = AppComponent.class, modules = {GameListModule.class})
public interface GameListComponent {
    void inject(GameListFragment gameListFragment);
}