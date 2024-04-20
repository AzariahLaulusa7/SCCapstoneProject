package com.example.guarden;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Calendar;

public class ChartsActivity extends Activity {
    private static final String PREFS_NAME = "MoodPrefsFile";
    private static final String PREF_PREFIX_KEY = "mood_";
    private MoodGraphView moodGraphView;
    private ImageView gameChallengeIconEmpty;
    private ImageView gameChallengeIconCheck;
    private ImageView breatheChallengeIconCheck;
    private ImageView breatheChallengeIconEmpty;
    private SharedPreferences sharedPreferences;

    private int[] moodValues;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_of_mind);

        moodValues = new int[7]; // Ensure this is initialized before any use
        Arrays.fill(moodValues, -1); // Optional: Initialize with a default value

        moodGraphView = findViewById(R.id.mood_graph_view);
        gameChallengeIconEmpty = findViewById(R.id.gameChallengeIconEmpty);
        gameChallengeIconCheck = findViewById(R.id.gameChallengeIconCheck);
        breatheChallengeIconCheck = findViewById(R.id.breatheChallengeIcon);
        breatheChallengeIconEmpty = findViewById(R.id.breatheChallengeIconEmpty);

        sharedPreferences = this.getSharedPreferences("DailyChallenges", Context.MODE_PRIVATE);
        loadMoodData();

        if (moodGraphView != null) {
            moodGraphView.setMoodValues(moodValues);
        } else {
            throw new RuntimeException("MoodGraphView not found.");
        }

        updateGameChallengeIcon();
        setupMidnightAlarm();

        setupMoodButton(R.id.sad_face_button, 0);
        setupMoodButton(R.id.ok_face_button, 1);
        setupMoodButton(R.id.happy_face_button, 2);

        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        resetMoodDataIfNecessary();
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
    private void resetMoodDataIfNecessary() {
        Calendar now = Calendar.getInstance();
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        int hourOfDay = now.get(Calendar.HOUR_OF_DAY);

        // Check if it's Sunday and past midnight but within a small buffer (e.g., 1 hour to avoid edge cases)
        if (dayOfWeek == Calendar.SUNDAY && hourOfDay == 0) {
            Arrays.fill(moodValues, -1);  // Reset all mood data
            saveMoodData();  // Save the reset state to SharedPreferences
            moodGraphView.setMoodValues(moodValues);  // Update the graph
            moodGraphView.invalidate();  // Redraw the graph
        }
    }


    private void loadMoodData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        for (int i = 0; i < moodValues.length; i++) {
            moodValues[i] = prefs.getInt(PREF_PREFIX_KEY + i, -1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetMoodDataIfNecessary();
        updateGameChallengeIcon();
    }

    private void updateGameChallengeIcon() {
        boolean isGameCompleted = sharedPreferences.getBoolean("MemoryGameCompleted", false);
        boolean isBreatheCompleted = sharedPreferences.getBoolean("BreatheCompleted", false);

        if (isGameCompleted) {
            gameChallengeIconEmpty.setVisibility(View.GONE);
            gameChallengeIconCheck.setVisibility(View.VISIBLE);
        } else {
            gameChallengeIconEmpty.setVisibility(View.VISIBLE);
            gameChallengeIconCheck.setVisibility(View.GONE);
        }
        if (isBreatheCompleted) {
            breatheChallengeIconEmpty.setVisibility(View.GONE);
            breatheChallengeIconCheck.setVisibility(View.VISIBLE);
        } else {
            breatheChallengeIconEmpty.setVisibility(View.VISIBLE);
            breatheChallengeIconCheck.setVisibility(View.GONE);
        }
    }
    private void setupMidnightAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MidnightResetReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1);  // Ensure this is going forward, not backward
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
    }


}

