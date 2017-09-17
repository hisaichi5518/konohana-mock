package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class LongTypeAdapter implements PreferenceTypeAdapter<Long> {
    @Override
    public Long get(SharedPreferences prefs, String key) {
        return get(prefs, key, 0L);
    }

    @Override
    public Long get(SharedPreferences prefs, String key, Long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, String key, Long value) {
        prefs.edit().putLong(key, value).apply();
    }
}
