package com.med.meditationsoundapp.SoundUtils;

import android.content.Context;
import android.content.SharedPreferences;

public class MedPref {
    public static final String MED_WALKTHROUGH = "APP_WALKTHROUGH";
    public static final String INT_DURATION = "INT_DURATION";
    public static final String INT_COUNT_DOWN = "INT_COUNT_DOWN";
    public static final String INT_DEAFULT_VOLUME = "INT_DEAFULT_VOLUME";
    public static final String INT_TIMER_INTERFACE = "INT_TIMER_INTERFACE";
    public static final String BOOL_NIGHT = "BOOL_NIGHT";
    public static final String BOOL_DEVICE = "BOOL_DEVICE";
    private SharedPreferences sharedPreferences;
    static final String MyPref = "MedPref";

    public static final String MED_AD_BACK = "MED_AD_BACK";
    public static final String MED_AD_BANNER = "MED_AD_BANNER";
    public static final String MED_AD_INTER = "MED_AD_INTER";
    public static final String MED_AD_NATIVE = "MED_AD_NATIVE";
    public static final String MED_AD_OPEN = "MED_AD_OPEN";
    public static final String MED_AD_COUNTER = "MED_AD_COUNTER";
    public static final String MED_SHOW = "MedAdShow";
    public static final String MED_CLICK = "MEDCLICK";
    public static String MED_openads;

    public MedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("med_pref", Context.MODE_PRIVATE);
    }

    public int getInt(String str, int i) {
        return sharedPreferences.getInt(str, i);
    }

    public String getString(String str, String s) {
        return sharedPreferences.getString(str, s);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String str, boolean i) {
        return sharedPreferences.getBoolean(str, i);
    }

    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public long getLong(String key, long i) {
        return sharedPreferences.getLong(key, i);
    }
}
