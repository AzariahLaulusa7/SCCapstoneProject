package com.example.guarden;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

public class MidnightResetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar now = Calendar.getInstance();
        int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
        SharedPreferences prefs = context.getSharedPreferences("MoodPrefsFile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Reset weekly data on Sunday
        if (dayOfWeek == Calendar.SUNDAY) {
            for (int i = 0; i < 7; i++) {
                editor.putInt("mood_" + i, -1);
            }
            editor.apply();
        }

        // Always reset daily challenges
        SharedPreferences dailyChallenges = context.getSharedPreferences("DailyChallenges", Context.MODE_PRIVATE);
        SharedPreferences.Editor dailyEditor = dailyChallenges.edit();
        dailyEditor.putBoolean("MemoryGameCompleted", false);
        dailyEditor.putBoolean("BreatheCompleted", false);
        dailyEditor.apply();
    }
}


