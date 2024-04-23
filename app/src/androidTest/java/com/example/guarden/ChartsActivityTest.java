package com.example.guarden;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class ChartsActivityTest {

    @Rule
    public ActivityTestRule<ChartsActivity> activityRule = new ActivityTestRule<>(ChartsActivity.class);

    // Tests that face displays on graph
    @Test
    public void testMoodButtonsUpdateGraph() {
        onView(withId(R.id.sad_face_button)).perform(click());
        onView(withId(R.id.mood_graph_view)).check(matches(isDisplayed()));

        onView(withId(R.id.happy_face_button)).perform(click());
    }
}
