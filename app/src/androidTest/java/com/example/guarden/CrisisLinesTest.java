package com.example.guarden;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static org.hamcrest.core.AllOf.allOf;

import android.content.Intent;
import android.net.Uri;

@RunWith(AndroidJUnit4.class)
public class CrisisLinesTest {

    @Rule
    public IntentsTestRule<CrisisLines> intentsTestRule = new IntentsTestRule<>(CrisisLines.class);

    // Test 988 call
    @Test
    public void testSuicideCrisisCall() {
        onView(withId(R.id.button_suicide_crisis)).perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_DIAL),
                hasData(Uri.parse("tel:988"))
        ));
    }

    // Test 911 call
    @Test
    public void testEmergencyServicesCall() {
        onView(withId(R.id.button_emergency_services)).perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_DIAL),
                hasData(Uri.parse("tel:911"))
        ));
    }

    // Test 471471 text
    @Test
    public void testCrisisTextLineMessage() {
        onView(withId(R.id.button_crisis_text_line)).perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("sms:741741"))
        ));
    }

    // Test view therapists button
    @Test
    public void testViewTherapistsNavigation() {
        onView(withId(R.id.button_view_therapists)).perform(click());
        intended(hasComponent(TherapistsActivity.class.getName()));
    }

    // Test chatbot button
    @Test
    public void testViewChatbotNavigation() {
        onView(withId(R.id.button_chatbot)).perform(click());
        intended(hasComponent(ChatbotUI.class.getName()));
    }
}
