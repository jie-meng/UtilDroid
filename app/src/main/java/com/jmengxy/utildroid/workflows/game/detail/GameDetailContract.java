package com.jmengxy.utildroid.workflows.game.detail;

import com.jmengxy.utildroid.models.GameCommentEntity;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utillib.architecture.mvp.BasePresenter;
import com.jmengxy.utillib.architecture.mvp.BaseView;

import java.util.List;

/**
 * Created by jiemeng on 04/02/2018.
 */

public interface GameDetailContract {
    interface View extends BaseView<Presenter> {
        void showProgress(boolean showOrHide);

        void showError(int code, String message);

        void showGame(GameEntity gameEntity);

        void showComments(List<GameCommentEntity> gameCommentEntities);

        void showRetry();
    }

    interface Presenter extends BasePresenter {
        void refresh();
    }
}
