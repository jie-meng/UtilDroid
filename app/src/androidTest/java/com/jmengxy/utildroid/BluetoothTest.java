package com.jmengxy.utildroid;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jmengxy.utildroid.utils.MockUtils;
import com.jmengxy.utildroid.utils.UtilDroidUtils;
import com.jmengxy.utildroid.utils.rules.CleanupDataRule;
import com.jmengxy.utildroid.utils.rules.NetworkIdlingResourceRule;
import com.jmengxy.utildroid.workflows.bluetooth.BluetoothDevicesActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.jmengxy.utildroid.utils.EspressoUtils.waitUIShort;

/**
 * Created by jiemeng on 27/02/2018.
 */

@RunWith(AndroidJUnit4.class)
public class BluetoothTest {

    @Rule
    public ActivityTestRule<BluetoothDevicesActivity> bluetoothDevicesActivity = new ActivityTestRule<>(BluetoothDevicesActivity.class, false, false);

    @Rule
    public NetworkIdlingResourceRule networkIdlingResourceRule = new NetworkIdlingResourceRule();

    @Rule
    public CleanupDataRule cleanupDataRule = new CleanupDataRule();

    @Test
    public void login_happy_path() {
        if (!MockUtils.isMock()) {
            return;
        }

        UtilDroidUtils.login("jie-meng", "S234234");
        init();

        waitUIShort();

        onView(withText("microphone")).check(matches(isDisplayed()));
    }

    private void init() {
        bluetoothDevicesActivity.launchActivity(new Intent());
    }
}
