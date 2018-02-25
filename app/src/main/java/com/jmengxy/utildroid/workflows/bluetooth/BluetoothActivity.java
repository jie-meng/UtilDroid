package com.jmengxy.utildroid.workflows.bluetooth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.utils.bluetooth.BluetoothHoster;
import com.jmengxy.utillib.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class BluetoothActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.swipe_refresh_layout)
//    SwipeRefreshLayout swipeRefreshLayout;
//
//    @BindView(R.id.recyclerview)
//    RecyclerView recyclerView;

    private BluetoothHoster bluetoothHoster;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.dark_green));
        setContentView(R.layout.activity_bluetooth);
        init();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    private void init() {
        bluetoothHoster = UtilApplication.get(this).getAppComponent().getBluetoothHoster();
        compositeDisposable = new CompositeDisposable();
        Disposable subscribe = bluetoothHoster.discoverDevices()
                .subscribeOn(UtilApplication.get(this).getAppComponent().getSchedulerProvider().io())
                .observeOn(UtilApplication.get(this).getAppComponent().getSchedulerProvider().ui())
                .subscribe(btDevice -> {
                    Toast.makeText(this, btDevice.toString(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
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
}
