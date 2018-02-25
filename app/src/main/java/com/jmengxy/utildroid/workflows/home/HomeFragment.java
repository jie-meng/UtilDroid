package com.jmengxy.utildroid.workflows.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jmengxy.utildroid.R;
import com.jmengxy.utillib.listeners.OnBackPressedListener;
import com.jmengxy.utillib.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class HomeFragment extends Fragment implements OnBackPressedListener {

    public static final String TAG = "HomeFragment";

    private Unbinder unbinder;

    private HomeViewPagerAdapter viewPagerAdapter;

    @BindView(R.id.navigation)
    BottomNavigationView navigationView;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        setSelectedTab(HomeViewPagerAdapter.TAB.Games);
        return view;
    }

    private void init() {
        navigationView.inflateMenu(R.menu.home_navigation_items);

        List<HomeViewPagerAdapter.TAB> tabList = new ArrayList<>(navigationView.getMenu().size());
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            MenuItem item = navigationView.getMenu().getItem(i);
            tabList.add(getTab(item.getItemId()));
        }
        viewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), tabList);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount() - 1);

        UiUtils.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            setSelectedTab(getTab(item.getItemId()));
            return true;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private HomeViewPagerAdapter.TAB getTab(int itemId) {
        switch (itemId) {
            case R.id.action_games:
                return HomeViewPagerAdapter.TAB.Games;
            case R.id.action_functions:
                return HomeViewPagerAdapter.TAB.Discover;
            case R.id.action_bankcard:
                return HomeViewPagerAdapter.TAB.BankCard;
            case R.id.action_profile:
                return HomeViewPagerAdapter.TAB.Profile;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void setSelectedTab(HomeViewPagerAdapter.TAB tab) {
        int position = viewPagerAdapter.getPosition(tab);
        viewPager.setCurrentItem(position, false);
        invalidateActionbar(position);
    }

    @Override
    public boolean onBackPressed() {
        Fragment currentPage = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
        return currentPage instanceof OnBackPressedListener && ((OnBackPressedListener) currentPage).onBackPressed();

    }

    public void setNavigationViewVisibility(boolean visibility) {
        navigationView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    private void invalidateActionbar(int position) {
        Fragment currentPage = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());
        if (currentPage == null || currentPage.getView() == null) {
            return;
        }

        Toolbar toolbar = currentPage.getView().findViewById(R.id.toolbar);
        if (toolbar == null) {
            return;
        }

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            Fragment fragment = getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + i);
            fragment.setHasOptionsMenu(i == position);
        }

        getActivity().invalidateOptionsMenu();
    }
}
