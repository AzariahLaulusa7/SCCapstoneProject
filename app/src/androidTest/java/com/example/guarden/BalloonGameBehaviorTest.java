package com.example.guarden;

import static android.os.Trace.isEnabled;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.pressBack;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class BalloonGameBehaviorTest {

    @Rule
    public ActivityTestRule<BalloonGame> activityRule = new ActivityTestRule<>(BalloonGame.class);

    // Test initial game setup
    @Test
    public void testGameStart() {
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.txtCurrentScore)).check(matches(withText("Score: 0")));
        onView(withId(R.id.txtTimer)).check(matches(withText("TIME REMAINING\n10 SECONDS")));
        onView(withId(R.id.txtGameOver)).check(matches(not(isDisplayed())));
        onView(withId(R.id.btnPlayAgain)).check(matches(not(isDisplayed())));
    }

    // Test score increase on click
    @Test
    public void testBalloonClickIncreasesScore() {
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.imageBalloon)).perform(click());
        onView(withId(R.id.txtCurrentScore)).check(matches(withText("Score: 1")));
    }

    // Test end of game timer
    @Test
    public void testGameEndsAfterTimeout() {
        onView(withId(R.id.startButton)).perform(click());
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.txtGameOver)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPlayAgain)).check(matches(isDisplayed()));
        onView(withId(R.id.imageBalloon)).check(matches(not(isEnabled())));
    }
}
