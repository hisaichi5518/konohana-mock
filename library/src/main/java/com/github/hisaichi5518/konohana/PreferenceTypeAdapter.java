package com.github.hisaichi5518.konohana;

import android.content.SharedPreferences;

public interface PreferenceTypeAdapter<T> {
    T get(SharedPreferences prefs, String key);

    T get(SharedPreferences prefs, String key, T defaultValue);

    void set(SharedPreferences prefs, String key, T value);
}
