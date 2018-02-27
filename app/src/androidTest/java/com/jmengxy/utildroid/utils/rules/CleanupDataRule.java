package com.jmengxy.utildroid.utils.rules;

import android.support.test.rule.UiThreadTestRule;

import com.jmengxy.utildroid.utils.UtilDroidUtils;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by jiemeng on 27/02/2018.
 */

public class CleanupDataRule extends UiThreadTestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        UtilDroidUtils.getApp().getAppComponent().getAccountHoster().deleteAccount();
        UtilDroidUtils.getApp().getAppComponent().getDataSource().clearData().blockingGet();

        return super.apply(base, description);
    }
}
