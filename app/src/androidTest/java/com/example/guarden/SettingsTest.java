package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Intent;

@RunWith(AndroidJUnit4.class)
public class SettingsTest {

    @Before
    public void setUp() {
        Intent startIntent = new Intent();
        startIntent.putExtra("key", "value");
        activityRule.launchActivity(startIntent);
    }

    // Give permission access for notifs
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(POST_NOTIFICATIONS);

    @Rule
    public ActivityTestRule<Settings> activityRule = new ActivityTestRule<>(Settings.class, true, true);

    // Test dark mode switch
    @Test
    public void testDarkModeSwitch() {
        onView(withId(R.id.Dark_Mode_Switch)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.Dark_Mode_Switch)).perform(click());
    }

    // Test notifications switch
    @Test
    public void testNotificationsSwitch(){
        onView(withId(R.id.NotificationSwitch)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.NotificationSwitch)).perform(click());
    }

    // Test back button
    @Test
    public void testNavigationFromBackButton() {
        onView(withId(R.id.Back_Button)).perform(click());
    }
}



