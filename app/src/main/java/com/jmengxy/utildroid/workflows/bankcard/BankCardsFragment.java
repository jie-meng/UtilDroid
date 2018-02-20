package com.jmengxy.utildroid.workflows.bankcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmengxy.utildroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class BankCardsFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static BankCardsFragment newInstance() {
        BankCardsFragment fragment = new BankCardsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bankcards, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        init();
        return rootView;
    }

    private void init() {

    }
}
