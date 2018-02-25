package com.jmengxy.utildroid.workflows.game.list;

import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utillib.architecture.mvp.BasePresenter;
import com.jmengxy.utillib.architecture.mvp.BaseView;

import java.util.List;

/**
 * Created by jiemeng on 04/02/2018.
 */

public interface GameListContract {
    interface View extends BaseView<Presenter> {
        void showProgress(boolean showOrHide);

        void showError(int code, String message);

        void showGames(List<GameEntity> gameEntities);
    }

    interface Presenter extends BasePresenter {
        void refresh();
    }
}
