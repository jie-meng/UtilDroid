package com.jmengxy.utildroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.jmengxy.utildroid.models.UserEntity;
import com.jmengxy.utillib.listeners.OnBackPressedListener;
import com.jmengxy.utillib.utils.UiUtils;

import icepick.Icepick;
import icepick.State;

public class MainActivity extends AppCompatActivity {

    @State
    UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null && fragment instanceof OnBackPressedListener) {
            if (((OnBackPressedListener) fragment).onBackPressed()) {
                return;
            }
        }

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private void init() {
        initData();
        initFragment();
    }

    private void initData() {
        if (userEntity == null) {
            userEntity = new UserEntity();
            System.out.println("Activity new >>>>>> " + Integer.toString(userEntity.hashCode()));
        } else {
            System.out.println("Acticity recreate >>>>>> " + Integer.toString(userEntity.hashCode()));
        }
    }

    private void initFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = FirstFragment.newInstance(userEntity);
            UiUtils.replaceFragmentAndAddToBackStack(getSupportFragmentManager(), fragment, R.id.content_frame, null);
        }
    }
}
