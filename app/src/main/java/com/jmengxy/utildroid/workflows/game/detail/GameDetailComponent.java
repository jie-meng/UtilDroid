package com.jmengxy.utildroid.workflows.game.detail;

import com.jmengxy.utildroid.app.AppComponent;
import com.jmengxy.utillib.architecture.dagger.FragmentScoped;

import dagger.Component;

/**
 * Created by jiemeng on 04/02/2018.
 */
@FragmentScoped
@Component(dependencies = AppComponent.class, modules = {GameDetailModule.class})
public interface GameDetailComponent {
    void inject(GameDetailFragment gameDetailFragment);
}