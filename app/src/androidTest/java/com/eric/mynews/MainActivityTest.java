package com.eric.mynews;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.eric.mynews.main.MainActivity;
import com.eric.mynews.server.FakeWeatherServer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_COARSE_LOCATION);

    @Before
    public void setUp() {
        FakeWeatherServer.getInstance()
                .reset();
    }

    @Test
    public void testHappyPath25Singapore() {
        launchActivity();
        String expected = String.format(getResourceString(R.string.temp_degree), 25);
        assertDisplayed(expected);
        assertDisplayed("Singapore");
    }

    @Test
    public void testErrorPath() {
        FakeWeatherServer.getInstance()
                .simulateError();
        launchActivity();
        String expected = getResourceString(R.string.error_msg);
        assertDisplayed(expected);
    }

    private String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources()
                .getString(id);
    }

    private void launchActivity() {
        activityTestRule.launchActivity(new Intent(InstrumentationRegistry.getInstrumentation()
                .getTargetContext(), MainActivity.class));
    }
}
