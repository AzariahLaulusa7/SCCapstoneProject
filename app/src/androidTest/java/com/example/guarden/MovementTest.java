package com.example.guarden;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MovementTest {

    @Rule
    public ActivityTestRule<Movement> activityRule = new ActivityTestRule<>(Movement.class);

    // Test back button
    @Test
    public void testBackButtonNavigatesHome() {
        onView(withId(R.id.movement_back)).perform(click());
    }
}

