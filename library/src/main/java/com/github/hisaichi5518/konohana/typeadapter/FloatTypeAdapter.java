package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class FloatTypeAdapter implements PreferenceTypeAdapter<Float> {
    @NonNull
    @Override
    public Float get(SharedPreferences prefs, @NonNull String key) {
        return get(prefs, key, 0F);
    }

    @NonNull
    @Override
    public Float get(SharedPreferences prefs, @NonNull String key, @NonNull Float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, @NonNull String key, @NonNull Float value) {
        prefs.edit().putFloat(key, value).apply();
    }
}
