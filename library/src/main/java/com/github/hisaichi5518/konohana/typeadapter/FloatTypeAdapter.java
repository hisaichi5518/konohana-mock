package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class FloatTypeAdapter implements PreferenceTypeAdapter<Float> {
    @Override
    public Float get(SharedPreferences prefs, String key) {
        return get(prefs, key, 0F);
    }

    @Override
    public Float get(SharedPreferences prefs, String key, Float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, String key, Float value) {
        prefs.edit().putFloat(key, value).apply();
    }
}
