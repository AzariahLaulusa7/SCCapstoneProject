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

    @Test
    public void testInitialConditions() {
        // Ensure the start button is displayed initially
        onView(withId(R.id.startButton)).check(matches(isDisplayed()));
        // Check that the score and instructions are not visible
        onView(withId(R.id.textViewScore)).check(matches(not(isDisplayed())));
        onView(withId(R.id.instructions)).check(matches(isDisplayed()));
    }

    @Test
    public void testStartGame() {
        // Click on the start button to begin the game
        onView(withId(R.id.startButton)).perform(click());
        // Check that the score and instructions views are gone
        onView(withId(R.id.textViewScore)).check(matches(not(isDisplayed())));
        onView(withId(R.id.instructions)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testGameLostPrematurely() {
        // Start the game
        onView(withId(R.id.startButton)).perform(click());
        // Click the main layout prematurely
        onView(withId(R.id.mainLayout)).perform(click());
        // Check if the text view updates to the lose message
        onView(withId(R.id.textViewScore)).check(matches(withText("You lose! Try again.")));
    }

    @Test
    public void testGameWon() {
        // Start the game
        onView(withId(R.id.startButton)).perform(click());
        // Mock the handler delay to simulate game ready for reaction
        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityRule.getActivity().readyForReaction = true;
                activityRule.getActivity().startTime = System.currentTimeMillis();
            }
        });
        // Click the main layout to win the game
        onView(withId(R.id.mainLayout)).perform(click());
        // Check if the score is displayed correctly
        onView(withId(R.id.textViewScore)).check(matches(isDisplayed()));
    }
}
