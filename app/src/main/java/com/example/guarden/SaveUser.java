package com.example.guarden;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveUser {
    static final String PREF_USER_NAME= "username";

    static final String PREF_NAME = "nametag";
    static final String PREF_THUMBS = "laulusa_thumb";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_NAME, userName);
        editor.commit();
    }

    public static String getName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_NAME, "");
    }

    public static void setThumbsDown(Context ctx, boolean thumbs)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_THUMBS, thumbs);
        editor.commit();
    }

    public static boolean getThumbsDown(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_THUMBS, false);
    }

    public static void clearUserName(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); // Clear all stored data
        editor.commit();
    }
}
