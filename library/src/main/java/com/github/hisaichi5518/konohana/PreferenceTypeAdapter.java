package com.github.hisaichi5518.konohana;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public interface PreferenceTypeAdapter<T> {
    T get(SharedPreferences prefs,  @NonNull String key);

    T get(SharedPreferences prefs,  @NonNull String key, T defaultValue);

    void set(SharedPreferences prefs, @NonNull String key, T value);
}
