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
public class StringTypeAdapterTest {

    private StringTypeAdapter adapter;
    private SharedPreferences prefs;

    @Before
    public void setup() {
        adapter = new StringTypeAdapter();
        prefs = RuntimeEnvironment.application.getSharedPreferences("tests", Context.MODE_PRIVATE);
    }

    @Test
    public void get_WithDefault() throws Exception {
        assertThat(adapter.get(prefs, "key", null)).isEqualTo(null);

        adapter.set(prefs, "key", "value");

        assertThat(adapter.get(prefs, "key", null)).isEqualTo("value");
    }

    @Test
    public void get_WithoutDefault() throws Exception {
        assertThat(adapter.get(prefs, "key")).isEqualTo(null);

        adapter.set(prefs, "key", "value");

        assertThat(adapter.get(prefs, "key")).isEqualTo("value");
    }

}