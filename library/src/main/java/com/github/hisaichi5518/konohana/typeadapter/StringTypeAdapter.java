package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class StringTypeAdapter {
    @Nullable
    public static String get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, null);
    }

    @Nullable
    public static String get(SharedPreferences prefs, @NonNull String key, @Nullable String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public static void set(SharedPreferences prefs, @NonNull String key, @Nullable String value) {
        prefs.edit().putString(key, value).apply();
    }
}
