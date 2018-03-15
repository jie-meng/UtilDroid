package com.jmengxy.utildroid;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jmengxy.utildroid.utils.rules.CleanupDataRule;
import com.jmengxy.utildroid.utils.rules.NetworkIdlingResourceRule;
import com.jmengxy.utildroid.workflows.home.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jmengxy.utildroid.utils.EspressoUtils.waitUIShort;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by jiemeng on 15/03/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule
    public NetworkIdlingResourceRule networkIdlingResourceRule = new NetworkIdlingResourceRule();

    @Rule
    public CleanupDataRule cleanupDataRule = new CleanupDataRule();

    @Test
    public void logout_and_cancel() {
        init();

        onView(withText(R.string.login)).perform(click());

        waitUIShort();

        ViewInteraction username = onView(withId(R.id.ed_username));
        ViewInteraction password = onView(withId(R.id.ed_password));

        username.perform(click(), replaceText("jie-meng"));
        password.perform(click(), replaceText("S12345678"));

        onView(allOf(withId(R.id.btn_login), withText("Login"))).perform(click());

        waitUIShort();

        // TODO: 15/03/2018 implement logout and cancel test here
    }

    private void init() {
        mainActivityTestRule.launchActivity(new Intent());
    }
}
