package com.example.guarden;

import android.app.Application;
import android.content.Context;

import com.example.guarden.BreathePreferences;

public class BreatheApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BreathePreferences.init(getApplicationContext());
    }

}
