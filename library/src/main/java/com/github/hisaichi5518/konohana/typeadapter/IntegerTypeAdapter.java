package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class IntegerTypeAdapter {
    public static int get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, 0);
    }

    public static int get(SharedPreferences prefs, @NonNull String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public static void set(SharedPreferences prefs, @NonNull String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }
}
