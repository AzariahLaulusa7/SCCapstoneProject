package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class Apptivity extends Application implements Application.ActivityLifecycleCallbacks {

    private int activeActivityCount = 0;
    public static boolean isAppClosed = false;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        String strNumber = String.valueOf(activeActivityCount);
        isAppClosed = false;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activeActivityCount++;
        isAppClosed = false;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activeActivityCount--;
        if(activeActivityCount == 0){
            isAppClosed = true;
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                NotificationScheduler scheduler = new NotificationScheduler(this);
                scheduler.scheduleNotification();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    // Other lifecycle callback methods...

    // Method to get the active activity count
    public int getActiveActivityCount() {
        return activeActivityCount;
    }
}