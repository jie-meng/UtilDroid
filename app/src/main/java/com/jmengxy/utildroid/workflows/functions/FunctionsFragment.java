package com.jmengxy.utildroid.workflows.functions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.workflows.bluetooth.BluetoothDevicesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class FunctionsFragment extends Fragment {

    private Unbinder unbinder;

    @OnClick(R.id.bluetooth)
    void clickBluetooth() {
        startActivity(new Intent(getContext(), BluetoothDevicesActivity.class));
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static FunctionsFragment newInstance() {
        FunctionsFragment fragment = new FunctionsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_discover, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        init();
        return rootView;
    }

    private void init() {
    }
}
