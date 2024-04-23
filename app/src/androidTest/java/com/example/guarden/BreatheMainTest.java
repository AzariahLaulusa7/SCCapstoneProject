package com.example.guarden;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class BreatheMainTest {

    @Rule
    public ActivityTestRule<BreatheMain> activityRule = new ActivityTestRule<>(BreatheMain.class);

    // Test initial animation
    @Test
    public void testStartAnimation() {
        onView(withId(R.id.start_breathing)).perform(click());
        onView(withId(R.id.breathe_in)).check(matches(isDisplayed()));
        onView(withId(R.id.nose)).check(matches(isDisplayed()));
        onView(withId(R.id.breathe_out)).check(matches(not(isDisplayed())));
        onView(withId(R.id.hold)).check(matches(not(isDisplayed())));
        onView(withId(R.id.mouth)).check(matches(not(isDisplayed())));
    }

    // Test breathing in and out animations change after starting
    @Test
    public void testAnimationCycle() throws InterruptedException {
        onView(withId(R.id.start_breathing)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.hold)).check(matches(isDisplayed()));
        Thread.sleep(2500);
        onView(withId(R.id.breathe_out)).check(matches(isDisplayed()));
        onView(withId(R.id.mouth)).check(matches(isDisplayed()));
    }
}
