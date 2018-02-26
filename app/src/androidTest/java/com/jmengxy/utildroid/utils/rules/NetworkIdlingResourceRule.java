package com.jmengxy.utildroid.utils.rules;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.rule.UiThreadTestRule;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.jmengxy.utildroid.app.UtilApplication;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiemeng on 26/02/2018.
 */

public class NetworkIdlingResourceRule extends UiThreadTestRule {
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                UtilApplication application = (UtilApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
                OkHttp3IdlingResource idlingResource = OkHttp3IdlingResource.create("OkHttp", application.getAppComponent().getOkHttpClient());
                IdlingPolicies.setIdlingResourceTimeout(1, TimeUnit.MINUTES);
                IdlingPolicies.setMasterPolicyTimeout(1, TimeUnit.MINUTES);
                Espresso.registerIdlingResources(idlingResource);
                try {
                    base.evaluate();
                } finally {
                    Espresso.unregisterIdlingResources(idlingResource);
                }
            }
        };
    }
}
