package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class BooleanTypeAdapter implements PreferenceTypeAdapter<Boolean> {
    @NonNull
    @Override
    public Boolean get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, false);
    }

    @NonNull
    @Override
    public Boolean get(SharedPreferences prefs, @NonNull String key, @NonNull Boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, @NonNull String key, @NonNull Boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }
}
