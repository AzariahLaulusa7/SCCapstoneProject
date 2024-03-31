package com.example.guarden;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class ChartsActivity extends Activity {
    private static final String PREFS_NAME = "MoodPrefsFile";
    private static final String PREF_PREFIX_KEY = "mood_";
    private MoodGraphView moodGraphView;

    private int[] moodValues; // Array to hold mood values for the week

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_of_mind);
        ImageView backIcon = findViewById(R.id.backIcon);

        moodValues = new int[7]; // Initialize mood array with default values
        loadMoodData(); // Load saved mood values

        moodGraphView = findViewById(R.id.mood_graph_view);
        moodGraphView.setMoodValues(moodValues);

        // Set up mood buttons
        setupMoodButton(R.id.sad_face_button, 0);
        setupMoodButton(R.id.ok_face_button, 1);
        setupMoodButton(R.id.happy_face_button, 2);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and return to the previous activity
                finish();
            }
        });
    }

    private void setupMoodButton(int buttonId, final int mood) {
        ImageButton button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
                moodValues[today] = mood;
                saveMoodData();
                moodGraphView.invalidate();
            }
        });
    }

    private void saveMoodData() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        for (int i = 0; i < moodValues.length; i++) {
            editor.putInt(PREF_PREFIX_KEY + i, moodValues[i]);
        }
        editor.apply();
    }

    private void loadMoodData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        for (int i = 0; i < moodValues.length; i++) {
            moodValues[i] = prefs.getInt(PREF_PREFIX_KEY + i, -1);
        }
    }
}
