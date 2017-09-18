package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class FloatTypeAdapter {
    public static float get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, 0F);
    }

    public static float get(SharedPreferences prefs, @NonNull String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    public static void set(SharedPreferences prefs, @NonNull String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }
}
