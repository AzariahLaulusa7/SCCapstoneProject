package com.example.guarden;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import android.content.BroadcastReceiver;

public class AlarmReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        // Update the quote
        HomeScreen hs = new HomeScreen();
        hs.updateQuoteIfNeeded();

        // Display the updated quote
        hs.displayQuote();
    }

}
