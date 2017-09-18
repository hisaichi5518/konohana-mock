package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

public class StringSetTypeAdapter {
    @Nullable
    public static Set<String> get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, null);
    }

    public static Set<String> get(SharedPreferences prefs, @NonNull String key, @Nullable Set<String> defaultValue) {
        return prefs.getStringSet(key, defaultValue);
    }

    public static void set(SharedPreferences prefs, @NonNull String key, @Nullable Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }
}
