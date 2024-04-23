package com.example.guarden;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class MemoryGameBehaviorTest {

    @Rule
    public ActivityTestRule<MemoryGame> activityRule = new ActivityTestRule<>(MemoryGame.class);

    // Test initial state of game
    @Test
    public void testInitialState() {
        onView(withId(R.id.startGameButton)).check(matches(isDisplayed()));
        onView(withId(R.id.playAgainButton)).check(matches(not(isDisplayed())));
        onView(withId(R.id.feedbackText)).check(matches(not(isDisplayed())));
    }

    // Check that game has started
    @Test
    public void testStartGame() {
        onView(withId(R.id.startGameButton)).perform(click());
        onView(withId(R.id.gridLayout)).check(matches(isDisplayed()));
        onView(withId(R.id.startGameButton)).check(matches(not(isDisplayed())));
        onView(withId(R.id.playAgainButton)).check(matches(not(isDisplayed())));
    }

}

