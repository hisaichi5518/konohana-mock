package com.github.hisaichi5518.konohana.prefsadapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class StringSetPrefsAdapterTest {

    private SharedPreferences prefs;

    @Before
    public void setup() {
        prefs = RuntimeEnvironment.application.getSharedPreferences("tests", Context.MODE_PRIVATE);
    }

    @Test
    public void get_WithDefault() throws Exception {
        assertThat(StringSetPrefsAdapter.get(prefs, "key", null)).isEqualTo(null);

        Set<String> strings = new HashSet<>();
        strings.add("hoge");

        StringSetPrefsAdapter.set(prefs, "key", strings);

        assertThat(StringSetPrefsAdapter.get(prefs, "key", null)).isEqualTo(strings);
    }

    @Test
    public void get_WithoutDefault() throws Exception {
        assertThat(StringSetPrefsAdapter.get(prefs, "key")).isEqualTo(null);

        Set<String> strings = new HashSet<>();
        strings.add("hoge");

        StringSetPrefsAdapter.set(prefs, "key", strings);

        assertThat(StringSetPrefsAdapter.get(prefs, "key")).isEqualTo(strings);
    }

}