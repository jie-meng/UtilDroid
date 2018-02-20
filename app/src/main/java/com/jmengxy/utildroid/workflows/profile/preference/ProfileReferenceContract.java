package com.jmengxy.utildroid.workflows.profile.preference;

import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utillib.architecture.mvp.BasePresenter;
import com.jmengxy.utillib.architecture.mvp.BaseView;

/**
 * Created by jiemeng on 16/09/2017.
 */

public interface ProfileReferenceContract {
    interface View extends BaseView<Presenter> {

        void showEditUsernameDialog(UserEntity userEntity);

        void showEditPasswordDialog(UserEntity userEntity);

        void showEditNicknameDialog(UserEntity userEntity);

        void showEditMobileNumberDialog(UserEntity userEntity);

        void showEditEmailDialog(UserEntity userEntity);

        void showError(Throwable e);

        void showProgress(boolean showOrHide);

        void showProfile(UserEntity userEntity);
    }

    interface Presenter extends BasePresenter {
        void editUsername();

        void editPassword();

        void editMobileNumber();

        void editEmail();

        void editNickname();

        void updateProfile(UserEntity userEntity);
    }
}
