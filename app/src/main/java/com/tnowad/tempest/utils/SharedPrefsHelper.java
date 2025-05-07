package com.tnowad.tempest.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsHelper {
    private static final String PREFS_NAME = "TempestPrefs";
    private static final String KEY_TEMP_UNIT = "temp_unit"; // "C" or "F"

    public static void saveTemperatureUnit(Context context, String unit) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_TEMP_UNIT, unit);
        editor.apply();
    }

    public static String getTemperatureUnit(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return preferences.getString(KEY_TEMP_UNIT, "C"); // Default is Celsius
    }
}
