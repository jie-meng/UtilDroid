package com.jmengxy.utildroid.workflows.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jmengxy.utildroid.R;
import com.jmengxy.utildroid.workflows.profile.logout.LogoutDialogFragment;
import com.jmengxy.utildroid.workflows.profile.preference.ProfilePreferenceFragment;
import com.jmengxy.utillib.utils.UiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class ProfileFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        init();
        return rootView;
    }

    private void init() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        Fragment fragment = getChildFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment == null) {
            fragment = ProfilePreferenceFragment.newInstance();
            UiUtils.replaceFragmentAndAddToBackStack(getChildFragmentManager(), fragment, R.id.content_frame, null);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            showLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLogout() {
        new LogoutDialogFragment()
                .show(getChildFragmentManager(), LogoutDialogFragment.TAG);
    }
}
