package com.jmengxy.utildroid.workflows.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmengxy.utildroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class WelcomeFragment extends Fragment {

    public static final String TAG = "WelcomeFragment";

    private Unbinder unbinder;

    @OnClick(R.id.login)
    void clickLogin() {
        ((MainActivity) getActivity()).login();
    }

    @OnClick(R.id.register)
    void clickRegister() {
        ((MainActivity) getActivity()).register();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
