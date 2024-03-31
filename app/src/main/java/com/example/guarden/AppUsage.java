package com.example.guarden;

import android.app.Activity;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AppUsage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!hasUsageStatsPermission()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            displayAppUsageStats();
        }
    }

    private boolean hasUsageStatsPermission() {
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        List<UsageStats> stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 60 * 60 * 24, time);
        return (stats != null && !stats.isEmpty());
    }

    private void displayAppUsageStats() {
        UsageStatsManager usm = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        TextView usageTextView = findViewById(R.id.usageTimeText);
        usageTextView.setText("Daily Usage:\n");

        for (int i = 6; i >= 0; i--) {
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            long startTime = calendar.getTimeInMillis();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            long endTime = calendar.getTimeInMillis() - 1;
            List<UsageStats> queryUsageStats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

            long totalTimeUsed = 0;
            for (UsageStats us : queryUsageStats) {
                if (us.getPackageName().equals(getPackageName())) {
                    totalTimeUsed = us.getTotalTimeInForeground();
                }
            }

            String dateString = sdf.format(calendar.getTime());
            usageTextView.append(dateString + ": " + formatMillis(totalTimeUsed) + "\n");
            calendar.add(Calendar.DAY_OF_YEAR, -1); // Reset the day for the next iteration
        }
    }

    private String formatMillis(long timeInMillis) {
        long minutes = (timeInMillis / 1000) / 60;
        long seconds = (timeInMillis / 1000) % 60;
        return minutes + " min " + seconds + " sec";
    }
}