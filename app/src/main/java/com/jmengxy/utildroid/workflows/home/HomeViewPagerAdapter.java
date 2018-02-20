package com.jmengxy.utildroid.workflows.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jmengxy.utildroid.workflows.bankcard.BankCardsFragment;
import com.jmengxy.utildroid.workflows.discover.DiscoverFragment;
import com.jmengxy.utildroid.workflows.profile.ProfileFragment;

import java.util.List;

/**
 * Created by jiemeng on 03/02/2018.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {

    private final List<TAB> tabs;

    public enum TAB {
        Discover, BankCard, Profile
    }

    public HomeViewPagerAdapter(FragmentManager fm, List<TAB> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    public int getPosition(TAB tab) {
        return tabs.indexOf(tab);
    }

    @Override
    public Fragment getItem(int position) {
        switch (tabs.get(position)) {
            case Discover:
                return DiscoverFragment.newInstance();
            case BankCard:
                return BankCardsFragment.newInstance();
            case Profile:
                return ProfileFragment.newInstance();
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
