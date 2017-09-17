package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class IntegerTypeAdapter implements PreferenceTypeAdapter<Integer> {
    @Override
    public Integer get(SharedPreferences prefs, String key) {
        return get(prefs, key, 0);
    }

    @Override
    public Integer get(SharedPreferences prefs, String key, Integer defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, String key, Integer value) {
        prefs.edit().putInt(key, value).apply();
    }
}
