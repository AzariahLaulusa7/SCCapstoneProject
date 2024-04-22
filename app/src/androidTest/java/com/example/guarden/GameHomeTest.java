package com.example.guarden;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class GameHomeTest {

    @Rule
    public IntentsTestRule<GameHome> intentsTestRule = new IntentsTestRule<>(GameHome.class);

    // Test reaction time game button
    @Test
    public void testReactionGameNavigation() {
        onView(withId(R.id.buttonReaction)).perform(click());
        intended(hasComponent(ReactionGame.class.getName()));
    }

    // Test balloon game button
    @Test
    public void testBalloonsGameNavigation() {
        onView(withId(R.id.buttonBalloons)).perform(click());
        intended(hasComponent(BalloonGame.class.getName()));
    }

    // Test memory game button
    @Test
    public void testMemoryGameNavigation() {
        onView(withId(R.id.buttonMemory)).perform(click());
        intended(hasComponent(MemoryGame.class.getName()));
    }

    // Test leaderboard button
    @Test
    public void testLeaderboardNavigation() {
        onView(withId(R.id.buttonLeaderboard)).perform(click());
        intended(hasComponent(LeaderBoard.class.getName()));
    }

}
