package com.fernandocejas.android10.order.presentation.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 *
 */
public class PreferencesUtility {

    private static final String PREF_NAME = "pref_ODME";
    public static final String PREF_TOKEN = "pref_access_token";
    public static final String PREF_FIRE_BASE_TOKEN = "pref_access_fire_base_token";

    private static PreferencesUtility mInstance;

    private SharedPreferences settings;

    public static synchronized PreferencesUtility getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferencesUtility(context);
        }
        return mInstance;
    }

    public PreferencesUtility(Context context) {
        this.settings = context.getSharedPreferences(PREF_NAME, 0);
    }

    public void removeAll() {
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.commit();
    }

    public void writeLong(String key, long value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void writeInt(String key, int value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void writeString(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void writeFloat(String key, float value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public long readLong(String key, long defValue) {
        return settings.getLong(key, defValue);
    }

    public int readInt(String key, int defValue) {
        return settings.getInt(key, defValue);
    }

    public String readString(String key, String defValue) {
        return settings.getString(key, defValue);
    }

    public boolean readBoolean(String key, boolean defValue) {
        return settings.getBoolean(key, defValue);
    }

    public float readFloat(String key) {
        return settings.getFloat(key, 0);
    }
}
