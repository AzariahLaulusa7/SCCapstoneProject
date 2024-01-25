package com.example.guarden;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ReactionGameBehaviorTest {

    @Rule
    public ActivityScenarioRule<ReactionGame> activityRule = new ActivityScenarioRule<>(ReactionGame.class);

    @Test
    public void testGameFlow() throws InterruptedException {
        // Start the game
        onView(withId(R.id.startButton)).perform(click());

        // Wait for the color to change (max 5 seconds)
        Thread.sleep(5000);

        // Click the reaction button
        onView(withId(R.id.reactionButton)).perform(click());

        // Check if the score is there (indicates completed game)
        onView(withId(R.id.textViewScore)).check(matches(withText(containsString("Your Time:"))));
    }
}
