package com.jmengxy.utildroid.workflows.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.models.LoginRequest;
import com.jmengxy.utillib.utils.UiUtils;
import com.jmengxy.utillib.views.fragment.FormFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import icepick.State;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class LoginFragment extends FormFragment implements LoginContract.View {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.til_username)
    TextInputLayout tilUsername;

    @BindView(R.id.ed_username)
    TextInputEditText edUsername;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.ed_password)
    TextInputEditText edPassword;

    @BindView(R.id.btn_login)
    Button loginButtion;

    @OnClick(R.id.btn_login)
    void clickLogin() {
        presenter.login();
    }

    @State
    LoginRequest loginRequest;

    @Inject
    LoginContract.Presenter presenter;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (loginRequest == null) {
            loginRequest = new LoginRequest();
        }

        DaggerLoginComponent.builder()
                .appComponent(UtilApplication.get(getContext()).getAppComponent())
                .loginModule(new LoginModule(this, loginRequest))
                .build()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        init();
        presenter.attach();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        presenter.detach();
        super.onDestroyView();
        unbinder.unbind();
    }

    private void init() {
        edUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setUsername(edUsername.getText().toString());
                presenter.nextStepEnableCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideError();
                presenter.nextStepEnableCheck();
            } else {
                presenter.checkUsername();
            }
        });

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.setPassword(edPassword.getText().toString());
                presenter.nextStepEnableCheck();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                hideError();
                presenter.nextStepEnableCheck();
            } else {
                presenter.checkPassword();
            }
        });
    }

    @Override
    public void showUsername(String username) {
        edUsername.setText(username);
    }

    @Override
    public void showPassword(String password) {
        edPassword.setText(password);
    }

    @Override
    public void showProgress(boolean showOrHide) {
        UiUtils.showOrHideProgressView(getActivity(), showOrHide, R.layout.view_progress_bar, R.id.progress_layout);
    }

    @Override
    public void gotoNextPage() {
        getActivity().finish();
    }

    @Override
    public void showError(int code, String message) {
        super.showError(message);
    }

    @Override
    public void showCheckUsernameResult(boolean passOrFail) {
        if (tilUsername == null) {
            return;
        }

        tilUsername.setError(passOrFail ? null : getString(R.string.invalid_username));
    }

    @Override
    public void showCheckPasswordResult(boolean passOrFail) {
        if (tilPassword == null) {
            return;
        }

        tilPassword.setError(passOrFail ? null : getString(R.string.invalid_password));
    }

    @Override
    public void enableLoginButton(boolean enable) {
        UiUtils.setViewClickable(loginButtion, enable);
    }
}
