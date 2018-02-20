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

        void showCheckUsernameResult(boolean passOrFail);

        void showCheckPasswordResult(boolean passOrFail);

        void enableLoginButton(boolean enable);
    }

    interface Presenter extends BasePresenter {
        void setUsername(String username);

        void setPassword(String password);

        void checkUsername();

        void checkPassword();

        void nextStepEnableCheck();

        void login();
    }
}
