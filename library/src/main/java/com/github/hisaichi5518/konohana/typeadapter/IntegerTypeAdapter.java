package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class IntegerTypeAdapter implements PreferenceTypeAdapter<Integer> {
    @NonNull
    @Override
    public Integer get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, 0);
    }

    @NonNull
    @Override
    public Integer get(SharedPreferences prefs, @NonNull String key, @NonNull Integer defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, @NonNull String key, @NonNull Integer value) {
        prefs.edit().putInt(key, value).apply();
    }
}
