package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

public class StringTypeAdapter implements PreferenceTypeAdapter<String> {
    @Override
    public String get(SharedPreferences prefs, String key) {
        return get(prefs, key, null);
    }

    @Override
    public String get(SharedPreferences prefs, String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, String key, String value) {
        prefs.edit().putString(key, value).apply();
    }
}
