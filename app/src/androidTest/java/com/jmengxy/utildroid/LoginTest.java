package com.jmengxy.utildroid;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jmengxy.utildroid.utils.UIAutomatorUtils;
import com.jmengxy.utildroid.utils.rules.CleanupDataRule;
import com.jmengxy.utildroid.utils.rules.NetworkIdlingResourceRule;
import com.jmengxy.utildroid.workflows.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jmengxy.utildroid.utils.EspressoUtils.isShowingPassword;
import static com.jmengxy.utildroid.utils.EspressoUtils.waitUIShort;
import static com.jmengxy.utildroid.utils.UtilDroidUtils.getApp;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by jiemeng on 26/02/2018.
 */

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule = new ActivityTestRule<>(LoginActivity.class, false, false);

    @Rule
    public NetworkIdlingResourceRule networkIdlingResourceRule = new NetworkIdlingResourceRule();

    @Rule
    public CleanupDataRule cleanupDataRule = new CleanupDataRule();

    @Test
    public void login_happy_path() {
        init();

        ViewInteraction username = onView(withId(R.id.ed_username));
        ViewInteraction password = onView(withId(R.id.ed_password));


        username.perform(click(), replaceText("jie-meng"));
        password.perform(click(), replaceText("12345678"));

        password.check(matches(isShowingPassword()));

        onView(withId(R.id.text_input_password_toggle)).perform(click());

        waitUIShort();

        password.check(matches(not(isShowingPassword())));

        onView(withId(R.id.text_input_password_toggle)).perform(click());

        onView(allOf(withId(R.id.btn_login), withText("Login"))).perform(click());

        waitUIShort();

        onView(withText(R.string.invalid_password)).check(matches(isDisplayed()));
    }

    @Test
    public void switch_background_and_forground() {
        init();

        ViewInteraction username = onView(withId(R.id.ed_username));
        ViewInteraction password = onView(withId(R.id.ed_password));

        username.perform(click(), replaceText("jie-meng"));
        password.perform(click(), replaceText("12345678"));

        password.check(matches(isShowingPassword()));

        onView(withId(R.id.text_input_password_toggle)).perform(click());

        waitUIShort();

        password.check(matches(not(isShowingPassword())));

        UIAutomatorUtils.pressHomeAndRestartAppFromLauncher(getApp());

        password.check(matches(not(isShowingPassword())));
    }

    private void init() {
        loginActivityTestRule.launchActivity(new Intent());
    }
}
