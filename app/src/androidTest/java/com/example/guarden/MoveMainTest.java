package com.example.guarden;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.After;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MoveMainTest {

    @Rule
    public IntentsTestRule<MoveMain> intentsTestRule = new IntentsTestRule<>(MoveMain.class, true, true);

    // Test yoga button
    @Test
    public void testYogaButtonNavigatesCorrectly() {
        onView(withId(R.id.yoga)).perform(click());
        intended(hasComponent(Movement.class.getName()));
    }

    // Test exercise button
    @Test
    public void testExerciseButtonNavigatesCorrectly() {
        onView(withId(R.id.exercise)).perform(click());
        intended(hasComponent(Movement.class.getName()));
    }

    // Test custom movement button
    @Test
    public void testCustomButtonNavigatesCorrectly() {
        onView(withId(R.id.custom)).perform(click());
        intended(hasComponent(MovementViewList.class.getName()));
    }
}
