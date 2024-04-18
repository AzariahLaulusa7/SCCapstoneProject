package com.example.guarden;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

//this counts app activity. its main purpose is to know when the app is closed.
public class Apptivity extends Application implements Application.ActivityLifecycleCallbacks {

    private int activeActivityCount = 0;
    public static boolean isAppClosed = false;

    //on creation of app initiliaze isAppClosed to false
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

    //every time an activity is started, increase count. sets isAppClosed to false if app is reopend.
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
//if activity is stopped decrease
    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activeActivityCount--;
        //activity is 0 so app is closed.
        if(activeActivityCount == 0){
            isAppClosed = true;
            //this should csll the method to schedule a notification 2 hours after it is closed.
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
