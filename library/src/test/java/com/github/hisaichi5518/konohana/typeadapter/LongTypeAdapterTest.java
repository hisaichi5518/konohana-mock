package com.github.hisaichi5518.konohana.typeadapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.hisaichi5518.konohana.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LongTypeAdapterTest {

    private SharedPreferences prefs;

    @Before
    public void setup() {
        prefs = RuntimeEnvironment.application.getSharedPreferences("tests", Context.MODE_PRIVATE);
    }

    @Test
    public void get_WithDefault() throws Exception {
        assertThat(LongTypeAdapter.get(prefs, "key", 0L)).isEqualTo(0L);

        LongTypeAdapter.set(prefs, "key", 1L);

        assertThat(LongTypeAdapter.get(prefs, "key", 0L)).isEqualTo(1L);
    }

    @Test
    public void get_WithoutDefault() throws Exception {
        assertThat(LongTypeAdapter.get(prefs, "key")).isEqualTo(0L);

        LongTypeAdapter.set(prefs, "key", 1L);

        assertThat(LongTypeAdapter.get(prefs, "key")).isEqualTo(1L);
    }

}