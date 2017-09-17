package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class LongTypeAdapter implements PreferenceTypeAdapter<Long> {
    @NonNull
    @Override
    public Long get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, 0L);
    }

    @NonNull
    @Override
    public Long get(SharedPreferences prefs, @NonNull String key, @NonNull Long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, @NonNull String key, @NonNull Long value) {
        prefs.edit().putLong(key, value).apply();
    }
}
