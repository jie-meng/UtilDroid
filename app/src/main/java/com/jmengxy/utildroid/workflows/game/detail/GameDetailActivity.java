package com.jmengxy.utildroid.workflows.game.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.models.GameEntity;
import com.jmengxy.utillib.listeners.OnBackPressedListener;
import com.jmengxy.utillib.utils.UiUtils;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class GameDetailActivity extends AppCompatActivity {

    public static final String ARG_GAME_ENTITY = "arg_game_entity";

    public static Intent getIntent(Context context, GameEntity gameEntity) {
        Intent intent = new Intent(context, GameDetailActivity.class);
        intent.putExtra(ARG_GAME_ENTITY, gameEntity);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_layout);
        init();
    }

    private void init() {
        UiUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.dark_green));
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = GameDetailFragment.newInstance(getIntent().getParcelableExtra(ARG_GAME_ENTITY));
            UiUtils.replaceFragmentAndAddToBackStack(getSupportFragmentManager(), fragment, R.id.content_frame, null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
