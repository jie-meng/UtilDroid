package com.jmengxy.utildroid.workflows.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.account_hoster.AccountHoster;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utillib.listeners.OnBackPressedListener;
import com.jmengxy.utillib.utils.UiUtils;

import butterknife.ButterKnife;

/**
 * Created by jiemeng on 25/01/2018.
 */

public class MainActivity extends AppCompatActivity {
    public static final String ARG_SHOW_LOGIN = "arg_show_login";

    private AccountHoster accountHoster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //prevent multiple instances of an activity when it is launched with different intents
        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }

        accountHoster = UtilApplication.get(this).getAppComponent().getAccountHoster();

        if (savedInstanceState == null) {
            if (getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)) {
                SystemClock.sleep(1500);
            }

            if (getIntent().getBooleanExtra(ARG_SHOW_LOGIN, false)) {
                login();
            }
        }

        setTheme(R.style.AppThemeBase);
        setContentView(R.layout.container_layout);
        ButterKnife.bind(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(ARG_SHOW_LOGIN, false)) {
            login();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accountHoster.isLoggedIn()) {
            Fragment homeFragment = getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);
            if (homeFragment == null) {
                UiUtils.replaceFragment(getSupportFragmentManager(), new HomeFragment(), R.id.content_frame, HomeFragment.TAG);
            }
        } else {
            Fragment welcomeFragment = getSupportFragmentManager().findFragmentByTag(WelcomeFragment.TAG);
            if (welcomeFragment == null) {
                UiUtils.replaceFragment(getSupportFragmentManager(), new WelcomeFragment(), R.id.content_frame, WelcomeFragment.TAG);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null && fragment instanceof OnBackPressedListener) {
            if (((OnBackPressedListener) fragment).onBackPressed()) {
                return;
            }
        }

        super.onBackPressed();
    }

    public void login() {
        accountHoster.startLogin(this, false);
    }

    public void register() {
        accountHoster.startLogin(this, true);
    }
}
