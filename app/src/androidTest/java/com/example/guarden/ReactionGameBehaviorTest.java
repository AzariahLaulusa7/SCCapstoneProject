package com.example.guarden;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.guarden.ReactionGame;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ReactionGameBehaviorTest {

    @Rule
    public ActivityTestRule<ReactionGame> activityRule = new ActivityTestRule<>(ReactionGame.class);

    // Test initial state of game
    @Test
    public void testInitialConditions() {
        onView(withId(R.id.startButton)).check(matches(isDisplayed()));
        onView(withId(R.id.textViewScore)).check(matches(not(isDisplayed())));
        onView(withId(R.id.instructions)).check(matches(isDisplayed()));
    }

    // Test score and instructions invisible after starting
    @Test
    public void testStartGame() {
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.textViewScore)).check(matches(not(isDisplayed())));
        onView(withId(R.id.instructions)).check(matches(not(isDisplayed())));
    }

    // Test premature click
    @Test
    public void testGameLostPrematurely() {
        // Start the game
        onView(withId(R.id.startButton)).perform(click());
        onView(withId(R.id.mainLayout)).perform(click());
        onView(withId(R.id.textViewScore)).check(matches(withText("You lose! Try again.")));
    }

    // Test winning game simulation
    @Test
    public void testReactionTime() {
        // Click 'start game' button
        onView(withId(R.id.startButton)).perform(click());

        // Wait for 6 seconds artificially (use Thread.sleep here for simplicity)
        try {
            Thread.sleep(6000);  // Wait for 6 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Click on the main layout to record end time and calculate reaction time
        onView(withId(R.id.mainLayout)).perform(click());

        // Check the TextView shows
        onView(withId(R.id.textViewScore)).check(matches(isDisplayed()));
    }


}
