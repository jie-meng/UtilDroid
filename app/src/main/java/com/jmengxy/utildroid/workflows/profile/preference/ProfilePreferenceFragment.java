package com.jmengxy.utildroid.workflows.profile.preference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.models.UserEntity;

import javax.inject.Inject;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class ProfilePreferenceFragment extends PreferenceFragmentCompat implements ProfileReferenceContract.View {

    public static final String TAG = "ProfilePreferenceFragment";

    @Inject
    ProfileReferenceContract.Presenter presenter;

    private Preference username;
    private Preference password;
    private Preference nickname;
    private Preference email;
    private Preference mobileNumber;

    public static Fragment newInstance() {
        return new ProfilePreferenceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerProfilePreferenceComponent
                .builder()
                .appComponent(UtilApplication.get(getContext()).getAppComponent())
                .profileReferenceModule(new ProfileReferenceModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.profile);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        initUsername();
        initPassword();
        initNickname();
        initMobileNumber();
        initEmail();
    }

    private void initEmail() {
        email = findPreference("email");
        email.setOnPreferenceClickListener(preference -> {
            presenter.editEmail();
            return true;
        });
    }

    private void initMobileNumber() {
        mobileNumber = findPreference("mobile_number");
        mobileNumber.setOnPreferenceClickListener(preference -> {
            presenter.editMobileNumber();
            return true;
        });
    }

    private void initNickname() {
        nickname = findPreference("nickname");
        nickname.setOnPreferenceClickListener(preference -> {
            presenter.editNickname();
            return true;
        });
    }

    private void initPassword() {
        password = findPreference("password");
        password.setOnPreferenceClickListener(preference -> {
            presenter.editPassword();
            return true;
        });
    }

    private void initUsername() {
        username = findPreference("username");
        username.setOnPreferenceClickListener(preference -> {
            presenter.editUsername();
            return true;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showEditUsernameDialog(UserEntity userEntity) {

    }

    @Override
    public void showEditPasswordDialog(UserEntity userEntity) {

    }

    @Override
    public void showEditNicknameDialog(UserEntity userEntity) {

    }

    @Override
    public void showEditMobileNumberDialog(UserEntity userEntity) {

    }

    @Override
    public void showEditEmailDialog(UserEntity userEntity) {

    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void showProgress(boolean showOrHide) {

    }

    @Override
    public void showProfile(UserEntity userEntity) {

    }
}
