package com.jmengxy.utildroid.workflows.bluetooth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.app.UtilApplication;
import com.jmengxy.utildroid.utils.bluetooth.BtDevice;
import com.jmengxy.utildroid.workflows.bluetooth.view_model.BluetoothDeviceAdapter;
import com.jmengxy.utillib.utils.UiUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jiemeng on 21/02/2018.
 */

public class BluetoothDevicesActivity extends AppCompatActivity implements BluetoothDevicesContract.View {

    @Inject
    BluetoothDevicesContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private BluetoothDeviceAdapter bluetoothDeviceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerBluetoothDevicesComponent
                .builder()
                .appComponent(UtilApplication.get(this).getAppComponent())
                .bluetoothDevicesModule(new BluetoothDevicesModule(this))
                .build()
                .inject(this);

        UiUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.dark_green));
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
        init();
        setSupportActionBar(toolbar);
    }

    private void init() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.green));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.scan();
        });

        bluetoothDeviceAdapter = new BluetoothDeviceAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(bluetoothDeviceAdapter);

        presenter.attach();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
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
    public void showProgress(boolean showOrHide) {
        swipeRefreshLayout.setRefreshing(showOrHide);
    }

    @Override
    public void showError(int code, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void foundNewDevice(BtDevice btDevice) {
        bluetoothDeviceAdapter.addData(btDevice);
    }
}
