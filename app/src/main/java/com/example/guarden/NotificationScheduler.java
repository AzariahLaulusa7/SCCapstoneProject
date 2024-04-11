package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class NotificationScheduler {
    private Context context;

    public NotificationScheduler(Context context) {
        this.context = context;
    }
    private static final int SCHEDULE_REQUEST_CODE = 123;
    private static final String CHANNEL_ID = "NotificationChannel";

    public static String rV;
    public static boolean isAppClosed;
    public static long triggerTimeMillis;
    @SuppressLint("ScheduleExactAlarm")
    public void scheduleNotification() {
        // Create an intent for the broadcast receiver
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Calculate the trigger time (one minute from now in EST)
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.add(Calendar.MINUTE, 1);
        long triggerTime = calendar.getTimeInMillis();

        // Schedule the alarm
        long windowStartTime = triggerTime - (10 * 1000); // 30 seconds before triggerTime
        long windowEndTime = triggerTime + (10 * 1000); // 30 seconds after triggerTime
        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, windowStartTime, windowEndTime, pendingIntent);
    }
}
