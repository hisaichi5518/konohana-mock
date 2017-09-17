package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class StringTypeAdapter implements PreferenceTypeAdapter<String> {
    @Nullable
    @Override
    public String get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, null);
    }

    @Nullable
    @Override
    public String get(SharedPreferences prefs, @NonNull String key, @Nullable String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, @NonNull String key, @Nullable String value) {
        prefs.edit().putString(key, value).apply();
    }
}
