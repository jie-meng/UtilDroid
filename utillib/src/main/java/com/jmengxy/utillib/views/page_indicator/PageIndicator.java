package com.jmengxy.utillib.views.page_indicator;

import android.support.v4.view.ViewPager;

/**
 * Created by jiemeng on 10/02/2018.
 */

public interface PageIndicator extends ViewPager.OnPageChangeListener {
    void setViewPager(ViewPager view);
}
