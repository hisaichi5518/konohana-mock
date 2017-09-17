package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class BooleanTypeAdapter implements PreferenceTypeAdapter<Boolean> {
    @Override
    public Boolean get(SharedPreferences prefs, String key) {
        return get(prefs, key, false);
    }

    @Override
    public Boolean get(SharedPreferences prefs, String key, Boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, String key, Boolean value) {
        prefs.edit().putBoolean(key, value).apply();;
    }
}
