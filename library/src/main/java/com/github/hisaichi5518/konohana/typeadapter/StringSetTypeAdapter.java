package com.github.hisaichi5518.konohana.typeadapter;

import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.PreferenceTypeAdapter;

import java.util.Set;

public class StringSetTypeAdapter implements PreferenceTypeAdapter<Set<String>> {
    @Override
    public Set<String> get(SharedPreferences prefs, String key) {
        return get(prefs, key, null);
    }

    @Override
    public Set<String> get(SharedPreferences prefs, String key, Set<String> defaultValue) {
        return prefs.getStringSet(key, defaultValue);
    }

    @Override
    public void set(SharedPreferences prefs, String key, Set<String> value) {
        prefs.edit().putStringSet(key, value).apply();
    }
}
