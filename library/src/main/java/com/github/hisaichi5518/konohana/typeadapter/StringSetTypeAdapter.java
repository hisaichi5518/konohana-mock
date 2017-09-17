package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

import java.util.Set;

public class StringSetTypeAdapter implements PreferenceTypeAdapter<Set<String>> {
    @Nullable
    @Override
    public Set<String> get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, null);
    }

    @Override
    public Set<String> get(SharedPreferences prefs, @NonNull String key, @Nullable Set<String> defaultValue) {
        return prefs.getStringSet(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, @NonNull String key, @Nullable Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }
}
