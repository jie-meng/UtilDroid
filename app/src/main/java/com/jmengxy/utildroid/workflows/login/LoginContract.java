package com.jmengxy.utildroid.workflows.login;

import com.jmengxy.utillib.architecture.mvp.BasePresenter;
import com.jmengxy.utillib.architecture.mvp.BaseView;

/**
 * Created by jiemeng on 04/02/2018.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showUsername(String username);

        void showPassword(String password);

        void showProgress(boolean showOrHide);

        void gotoNextPage();

        void showError(int code, String message);
    }

    interface Presenter extends BasePresenter {
        void setUsername(String username);

        void setPassword(String password);

        void gotoNext();
    }
}
